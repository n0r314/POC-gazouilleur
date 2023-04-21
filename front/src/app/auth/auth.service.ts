import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable, switchMap, tap} from 'rxjs';
import {AuthOutputDto} from "./auth.output.dto";

export type Credentials = {
  username: string;
  password: string;
};

export type RegisterInputDto = {
  username: string;
  nickname: string;
  password: string;
};

/**
 * Effectue des appels HTTP à l'API REST /api/auth pour gérer les fonctionnalités d'auhentification.
 */
@Injectable()
export class AuthService {
  private readonly baseUrl = '/api/auth';

  constructor(private httpClient: HttpClient) {}

  login(credentials: Credentials): Observable<AuthOutputDto> {
    console.log(`Je lance un POST sur ${this.baseUrl}/login`);
    return this.httpClient.post<AuthOutputDto>(`${this.baseUrl}/login`, credentials).pipe(
      tap({
          next: authOutputDto => {
            console.log(`J'ai trouvé l'utilisateur ${authOutputDto.user.username}`);
            sessionStorage.setItem("auth", JSON.stringify(authOutputDto))
          },
          error: err => {
            console.log(`Problème en faisant un POST sur ${this.baseUrl}}/login`, err);
            this.logout()
          }
        }
      )
    );
  }

  me(): Observable<AuthOutputDto> {
      return this.httpClient.get<AuthOutputDto>(`${this.baseUrl}/me`).pipe(
        tap({
          next: authOutputDto => {
            if (authOutputDto.user != null) {
              console.log(`J'ai trouvé l'utilisateur ${authOutputDto.user.username}`)
            } else {
              console.log(`pas d'utilisateur authentifié`)
            }
          },
          error: err => console.log(`Problème en faisant un GET sur ${this.baseUrl}}/me`, err)
        })
      )
  }

  logout(): void {
    sessionStorage.removeItem("auth")
  }

  register(input: RegisterInputDto): Observable<AuthOutputDto> {
    console.log(`Je lance un POST sur ${this.baseUrl}/register`);
    return this.httpClient.post<AuthOutputDto>(`${this.baseUrl}/register`, input).pipe(
      tap({
          next: authOutputDto => {
            console.log(`J'ai créé l'utilisateur ${authOutputDto.user.username}`);
            sessionStorage.setItem("auth", JSON.stringify(authOutputDto))
          },
          error: err => {
            console.log(`Problème en faisant un POST sur ${this.baseUrl}}/register`, err);
            this.logout()
          }
        }
      ),
      switchMap(() => this.login({
        username: input.username,
        password: input.password
      }))
    );
  }



  public basicAuthToken() {
    return "Basic " + JSON.parse(sessionStorage.getItem("auth")!).token;
  }

  isLoggedIn() {
    return !!(sessionStorage.getItem("auth"));
  }
}
