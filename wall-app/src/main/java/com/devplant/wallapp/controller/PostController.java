package com.devplant.wallapp.controller;

import com.devplant.wallapp.exceptions.ForbiddenException;
import com.devplant.wallapp.exceptions.ObjectNotFoundException;
import com.devplant.wallapp.model.Post;
import com.devplant.wallapp.model.PostRequest;
import com.devplant.wallapp.repo.PostRepo;
import com.devplant.wallapp.repo.ReplyRepo;
import com.devplant.wallapp.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;

@RestController
@RequestMapping("/api/posts")
@Api(description = "Create, Delete, Update and List Posts", tags = "posts")
public class PostController {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ReplyRepo replyRepo;

    @Autowired
    private AccountService accountService;

    @GetMapping()
    @ApiOperation(value = "Get Posts in a paged manner", nickname = "posts")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Request Successful"),
            @ApiResponse(code = 401, message = "Unauthorized, you're not logged in") })
    public Page<Post> posts(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return this.postRepo.findAll(new PageRequest(page, pageSize, new Sort(Sort.Direction.DESC, "creationDate")));
    }

    @Transactional
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{postId}")
    @ApiOperation(value = "Delete a specific posts", nickname = "deletePost")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Request Successful, post has been deleted"),
            @ApiResponse(code = 401, message = "Unauthorized, you're not logged in"),
            @ApiResponse(code = 403, message = "Forbidden, you can only delete your own posts"),
            @ApiResponse(code = 404, message = "Post with given Id not found") })
    public void delete(@PathVariable("postId") long postId, Principal principal) {
        Post post = this.postRepo.findOne(postId);
        if (post == null) {
            throw new ObjectNotFoundException("Post with id: " + postId + " not found");
        }
        if (post.getAuthor().getUsername().equals(principal.getName())) {
            this.replyRepo.deleteByPost(post);
            this.postRepo.delete(postId);
        } else {
            throw new ForbiddenException("Cannot delete posts of other users!");
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{postId}")
    @ApiOperation(value = "Update a specific posts", nickname = "updatePost")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Request Successful, post has been updated"),
            @ApiResponse(code = 401, message = "Unauthorized, you're not logged in"),
            @ApiResponse(code = 403, message = "Forbidden, you can only update your own posts"),
            @ApiResponse(code = 404, message = "Post with given Id not found"),
            @ApiResponse(code = 412, message = "Post to short, min length = 10") })
    public void update(@PathVariable("postId") long postId, @RequestBody PostRequest postRequest, Principal principal) {
        Post post = this.postRepo.findOne(postId);
        if (post == null) {
            throw new ObjectNotFoundException("Post with id: " + postId + " not found");
        }
        if (post.getAuthor().getUsername().equals(principal.getName())) {
            post.setContent(postRequest.getContent());
            this.postRepo.save(post);
        } else {
            throw new ForbiddenException("Cannot delete posts of other users!");
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping()
    @ApiOperation(value = "Create a new post", nickname = "createPost")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Request Successful, post has been created"),
            @ApiResponse(code = 401, message = "Unauthorized, you're not logged in"),
            @ApiResponse(code = 412, message = "Post to short, min length = 10") })
    public void create(@RequestBody PostRequest postRequest) {
        Post post = Post.builder().creationDate(new Date()).author(accountService.me())
                .content(postRequest.getContent()).build();
        postRepo.save(post);
    }

}
