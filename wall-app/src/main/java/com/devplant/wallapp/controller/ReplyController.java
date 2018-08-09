package com.devplant.wallapp.controller;

import com.devplant.wallapp.exceptions.ForbiddenException;
import com.devplant.wallapp.exceptions.ObjectNotFoundException;
import com.devplant.wallapp.model.Post;
import com.devplant.wallapp.model.Reply;
import com.devplant.wallapp.model.ReplyRequest;
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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;

@RestController
@RequestMapping("/api/replies")
@Api(description = "Create, Delete, Update and List Replies", tags = "replies")
public class ReplyController {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ReplyRepo replyRepo;

    @Autowired
    private AccountService accountService;

    @GetMapping("/{postId}")
    @ApiOperation(value = "Get replies to a post in a paged manner", nickname = "replies")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Request Successful"),
            @ApiResponse(code = 401, message = "Unauthorized, you're not logged in"),
            @ApiResponse(code = 404, message = "Post with given Id not found") })
    public Page<Reply> posts(@PathVariable("postId") long postId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Post post = this.postRepo.findOne(postId);
        if (post == null) {
            throw new ObjectNotFoundException("Post with id: " + postId + " not found");
        }
        return replyRepo
                .findAllByPost(post, new PageRequest(page, pageSize, new Sort(Sort.Direction.DESC, "creationDate")));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{replyId}")
    @ApiOperation(value = "Delete a specific reply", nickname = "deleteReply")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Request Successful, reply has been deleted"),
            @ApiResponse(code = 401, message = "Unauthorized, you're not logged in"),
            @ApiResponse(code = 403, message = "Forbidden, you can only delete your own posts"),
            @ApiResponse(code = 404, message = "Reply with given Id not found") })
    public void delete(@PathVariable("replyId") long replyId, Principal principal) {
        Reply reply = this.replyRepo.findOne(replyId);
        if (reply == null) {
            throw new ObjectNotFoundException("Reply with id: " + replyId + " not found");
        }
        if (reply.getAuthor().getUsername().equals(principal.getName())) {
            this.replyRepo.delete(replyId);
        } else {
            throw new ForbiddenException("Cannot delete replies of other users!");
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{replyId}")
    @ApiOperation(value = "Update a specific reply", nickname = "updateReply")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Request Successful, reply has been updated"),
            @ApiResponse(code = 401, message = "Unauthorized, you're not logged in"),
            @ApiResponse(code = 403, message = "Forbidden, you can only delete your own posts"),
            @ApiResponse(code = 404, message = "Reply with given Id not found"),
            @ApiResponse(code = 412, message = "Reply to short, min length = 10")})
    public void update(@PathVariable("replyId") long replyId, @RequestBody ReplyRequest replyRequest,
            Principal principal) {
        Reply reply = this.replyRepo.findOne(replyId);
        if (reply == null) {
            throw new ObjectNotFoundException("Post with id: " + replyId + " not found");
        }
        if (reply.getAuthor().getUsername().equals(principal.getName())) {
            reply.setContent(replyRequest.getContent());
            this.replyRepo.save(reply);
        } else {
            throw new ForbiddenException("Cannot delete posts of other users!");
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{postId}")
    @ApiOperation(value = "Add a reply to a specific post", nickname = "addReply")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Request Successful, reply has been added"),
            @ApiResponse(code = 401, message = "Unauthorized, you're not logged in"),
            @ApiResponse(code = 404, message = "Post with given Id not found"),
            @ApiResponse(code = 412, message = "Reply to short, min length = 10")})
    public void create(@PathVariable("postId") long postId, @RequestBody ReplyRequest replyRequest) {
        Post post = this.postRepo.findOne(postId);
        if (post == null) {
            throw new ObjectNotFoundException("Post with id: " + postId + " not found");
        }

        Reply reply = Reply.builder().creationDate(new Date()).author(accountService.me()).post(post)
                .content(replyRequest.getContent()).build();
        replyRepo.save(reply);
    }

}
