import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Login} from '../model/login';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import {Router} from '@angular/router';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/of';
import {Account} from '../model/account';

@Injectable()
export class AuthService {

  private currentUser: Account;


  constructor(private httpClient: HttpClient, private router: Router) {
  }

  login(username: string, password: string): Observable<Account> {
    return this.httpClient.post<Account>('/api/accounts/login', new Login(username, password))
      .do(user => this.currentUser = user);
  }

  isAuthenticated(): Observable<any> {
    if (this.currentUser) {
      return Observable.of(true);
    }
    return this.httpClient.get<Account>('/api/accounts/me').do(user => {
      this.currentUser = user;
    })
  }

  register(username: string, password: string) {
    return this.httpClient.post<Account>('/api/accounts/register', new Login(username, password));
  }

  logout() {
    this.httpClient.post<any>('/api/accounts/logout', {}).subscribe(() => {
      this.router.navigate(['/login']);
    })
  }

  getCurrentUser() {
    return this.currentUser;
  }
}
