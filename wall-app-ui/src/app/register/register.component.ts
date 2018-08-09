import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from '../common/services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  username: string;
  password: string;

  error: string ;

  constructor(private authService: AuthService, private router: Router) {
  }

  ngOnInit() {
  }

  register() {
    this.authService.register(this.username, this.password).subscribe(() => {
      this.router.navigate(['/login'])
    }, (error) => {
      this.error = error.message;
    })
  }

}
