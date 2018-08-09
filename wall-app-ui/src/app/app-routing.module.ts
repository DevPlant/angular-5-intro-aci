import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {WallComponent} from './wall/wall.component';
import {LoginGuard} from './common/guard/login.guard';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'wall',
    pathMatch: 'full'
  }, {
    component: LoginComponent,
    path: 'login'
  }, {
    component: RegisterComponent,
    path: 'register'
  }, {
    component: WallComponent,
    canActivate: [LoginGuard],
    path: 'wall'
  }, {
    path: '**',
    redirectTo: 'wall',
    pathMatch: 'full'
  },];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
