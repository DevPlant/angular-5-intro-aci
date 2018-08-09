import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  newUsername: string;
  newPassword: string;

  constructor(private httpClient: HttpClient) {
  }

  ngOnInit() {
  }

  login() {
    const request = {
      password: this.newPassword,
      username: this.newUsername
    };
    this.httpClient.post<void>('/api/accounts/login', request)
      .subscribe(
        (success) => console.log('success', success),
        (error) => console.log('error', error)
      )

  }

}
