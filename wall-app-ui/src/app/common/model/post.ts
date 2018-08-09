import {Reply} from './reply';

export class Post{

  id: number;
  content: string;
  creationDate: number;
  authorName: string;
  replies: Reply[];
  repliesShown: boolean;
  newReply: string;

}
