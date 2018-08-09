import {Injectable} from '@angular/core';
import {Page} from '../../common/model/page';
import {Post} from '../../common/model/post';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Reply} from '../../common/model/reply';

@Injectable()
export class WallService {

  constructor(private httpClient: HttpClient) {
  }


  getPosts(page: number = 0, pageSize: number = 30): Observable<Page<Post>> {
    const parameters = new HttpParams().set('page', `${page}`).set('pageSize', `${pageSize}`);
    return this.httpClient.get<Page<Post>>('/api/posts', {params: parameters});

  }

  addPost(newPostContent: string) {
    return this.httpClient.put('/api/posts', {content: newPostContent});
  }

  getReplies(postId: number,page: number = 0, pageSize: number = 30): Observable<Page<Reply>> {
    const parameters = new HttpParams().set('page', `${page}`).set('pageSize', `${pageSize}`);
    return this.httpClient.get<Page<Reply>>(`/api/replies/${postId}`, {params: parameters});
  }

  addReply(postId: number, newReply: string) {
    return this.httpClient.put(`/api/replies/${postId}`,{content: newReply});
  }

  deletePost(postId: number) {
    return this.httpClient.delete(`/api/posts/${postId}`);
  }

  deleteReply(replyId: number) {
    return this.httpClient.delete(`/api/replies/${replyId}`);
  }
}
