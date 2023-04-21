import { CommonModule } from '@angular/common';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthRoutingModule } from './auth-routing.module';
import { AuthService } from './auth.service';
import { LoginFormComponent } from './login-form/login-form.component';
import { RegisterFormComponent } from './register-form/register-form.component';
import {BasicAuthInterceptor} from "./basic-auth.interceptor";

/**
 * Module d'authentification :
 * - page de connexion (LoginFormComponent)
 * - page d'inscription (RegisterFormComponent)
 * - service d'authentification (AuthService)
 * - intercepteur d'authentification (AuthInterceptor)
 */
@NgModule({
  imports: [CommonModule, FormsModule, HttpClientModule, AuthRoutingModule],
  providers: [
    AuthService,
    { provide: HTTP_INTERCEPTORS, useClass: BasicAuthInterceptor, multi: true },
  ],
  declarations: [LoginFormComponent, RegisterFormComponent],
})
export class AuthModule {}
