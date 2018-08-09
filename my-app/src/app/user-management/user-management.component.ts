import {Component} from '@angular/core';


class User {
  constructor(public username: string,
              public password: string){

  }
}

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.css']
})
export class UserManagementComponent {

  newUsername: string;
  newPassword: string;

  users: User[] = [];

  constructor() {
    const timo =new User('timo','pwd1');
    this.users.push(
      new User('start','pwd2'));
    this.users.push(timo);
    this.users.push(timo);
    this.users.push(
      new User('mid','pwd2'));
    this.users.push(timo);
    this.users.push(timo);
    this.users.push(
      new User('end','pwd2'));

   // this.users = this.users.filter((user)=> user.username.startsWith("t"));

   // const timo = this.users.find((user)=> user.username === 'timo');
  }

  delete(userToDelete: User){

    console.log('should delete', userToDelete);

    const indexOfUser = this.users.findIndex((user) => user.username === userToDelete.username);

    this.users.splice(indexOfUser, 1);

    //this.users.splice(this.users.indexOf(userToDelete),1);
  }

  deleteByIndex(index: number){
    this.users.splice(index, 1);
  }


  saveUser() {
    const newUser = new User(this.newUsername,
      this.newPassword);
    this.users.push(newUser);
  }

}
