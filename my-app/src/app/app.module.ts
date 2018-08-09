import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {UserManagementComponent} from './user-management/user-management.component';
import {FormsModule} from '@angular/forms';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {WallComponent} from './wall/wall.component';
import {RouterModule, Routes} from '@angular/router';
import {HttpClientModule} from '@angular/common/http';


export const routes : Routes = [
  {
    component: LoginComponent,
    path: 'login'
  },
  {
    component: RegisterComponent,
    path: 'register'
  },
  {
    component: WallComponent,
    path: 'wall'
  }
];

@NgModule({
  declarations: [
    AppComponent,
    UserManagementComponent,
    LoginComponent,
    RegisterComponent,
    WallComponent
  ],
  imports: [
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes),
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
