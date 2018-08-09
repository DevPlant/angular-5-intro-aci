import {Component, OnInit} from '@angular/core';
import {Post} from '../common/model/post';
import {AuthService} from '../common/services/auth.service';
import {WallService} from './services/wall.service';
import {Account} from '../common/model/account';
import {Reply} from '../common/model/reply';

@Component({
  selector: 'app-wall',
  templateUrl: './wall.component.html',
  styleUrls: ['./wall.component.scss']
})
export class WallComponent implements OnInit {

  posts: Post[];
  currentPage = 0;
  lastPage: boolean;

  newPostContent: string;
  currentUser: Account;

  constructor(private authService: AuthService, private wallService: WallService) {
  }

  ngOnInit() {
    this.currentUser = this.authService.getCurrentUser();
    this._loadInitialPosts();
  }

  loadMorePosts() {
    if (!this.lastPage) {
      this.currentPage += 1;
      this.wallService.getPosts(this.currentPage).subscribe(postsPage => {
        this.posts = this.posts.concat(postsPage.content);
        this.lastPage = postsPage.last;
      });
    }
  }

  addPost() {
    this.wallService.addPost(this.newPostContent).subscribe(() => {
      this._loadInitialPosts();
    })
  }

  logout() {
    this.authService.logout();
  }


  private _loadInitialPosts() {
    this.wallService.getPosts(this.currentPage).subscribe(postsPage => {
      this.posts = postsPage.content;
      this.lastPage = postsPage.last;
    });
  }


  showReplies(post: Post) {
    this.wallService.getReplies(post.id).subscribe(replyPage => {
      post.replies = replyPage.content;
      post.repliesShown = true;
    })
  }

  addReply(post: Post) {
    this.wallService.addReply(post.id, post.newReply).subscribe(() => {
      this.showReplies(post);
    });

  }

  deletePost(post: Post) {
    this.wallService.deletePost(post.id).subscribe(() => {
      this.posts.splice(this.posts.findIndex(p => p.id == post.id), 1);
    })
  }

  deleteReply(post: Post, reply: Reply) {
    this.wallService.deleteReply(reply.id).subscribe(() => {
      console.log(post.replies.findIndex(r => r.id === reply.id));
      post.replies.splice(post.replies.findIndex(r => r.id === reply.id), 1);
    })
  }
}
