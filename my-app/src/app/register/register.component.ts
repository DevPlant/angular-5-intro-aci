import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  newUsername: string;
  newPassword: string;

  constructor(private httpClient: HttpClient) {
  }

  ngOnInit() {
  }

  register() {
    const request = {
      password: this.newPassword,
      username: this.newUsername
    };
    this.httpClient.post<void>('/api/accounts/register', request)
      .subscribe(
        (success) => console.log('success', success),
        (error) => console.log('error', error)
      )

  }

}
