import {Component, OnInit} from '@angular/core';
import {AuthService} from '../common/services/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  username: string;
  password: string;

  error = false ;

  constructor(private authService: AuthService, private router: Router) {
  }

  ngOnInit() {
  }

  login() {
    this.authService.login(this.username, this.password).subscribe(() => {
      this.router.navigate(['/wall'])
    }, () => {
      this.error = true;
    })
  }

}
