import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import {AuthService} from "./auth.service";
import {Router} from "@angular/router";


/**
 * Intercepte les requêtes HTTP pour injecter le token de l'utilisateur dans les headers.
 */
@Injectable()
export class BasicAuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService, private router: Router) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    let req = request;
    if (sessionStorage.getItem("auth")) {
      const headerObject = { Authorization: this.authService.basicAuthToken() };
      req = request.clone({ setHeaders: {...headerObject }});
    }
    return next.handle(req).pipe(
      catchError(
        (err) => {
          if (err.status == "401") {
            this.authService.logout();
            this.router.navigateByUrl("/login").then();
          }
          return throwError(err);
        }
      )
    );
  }
}
