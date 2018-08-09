import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {AuthService} from '../services/auth.service';
import 'rxjs/add/observable/of';

@Injectable()
export class LoginGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {

  }

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return Observable.create(obs => {
      this.authService.isAuthenticated().subscribe(() => {
        obs.next(true);
        obs.complete();
      }, () => {
        this.router.navigate(['/login']);
        obs.next(false);
        obs.complete();
      });
    });
  }
}
