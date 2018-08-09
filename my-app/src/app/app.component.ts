import { Component } from '@angular/core';



class CompactUser{

  constructor(public username: string,
              public password: string){
  }

}

class User {

  username: string;
  password: string;

  constructor(username: string, password: string){
    this.username = username;
    this.password = password;
  }
}

interface IUser {
  username: string;
  password: string;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';

  iUser : IUser = {
    username: 'Lala',
    password: 'somePwd'
  };

  public name : string = 'Timo Bejan';

  public user : IUser
    = new User('tbejan','mypwd');

  compactUser : IUser
    = new CompactUser('timo2', 'otherpwd');

  changePwd(){
    this.user.password = 'New Password';
  }

}
