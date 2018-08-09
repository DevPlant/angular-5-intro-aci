import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';


class Page<T>{

  content: T[];

}

class Post {
  authorName: string;
  creationDate: number;
  content: string;
  id: number;
}

@Component({
  selector: 'app-wall',
  templateUrl: './wall.component.html',
  styleUrls: ['./wall.component.css']
})
export class WallComponent implements OnInit {


  public currentPosts: Post[] = [];
  public newPost: string;

  constructor(private httpClient: HttpClient) {
  }

  ngOnInit() {
    this.loadPosts();
  }

  loadPosts(){
    this.httpClient.get<Page<Post>>('/api/posts').subscribe((postsPage) => {
      this.currentPosts = postsPage.content;
      console.log('da, poti');
    }, (myError) => {
      console.log('ERROR', myError);
    })
  }

  addPost(){
    this.httpClient.put('/api/posts',{ content: this.newPost}).subscribe(()=>{
      console.log("POST ADDED!");
      this.loadPosts();
    })
  }

}
