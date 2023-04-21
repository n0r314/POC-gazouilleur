import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable, of, switchMap, tap} from "rxjs";
import {UserDto} from "./user.dto";
import {AuthService} from "../auth/auth.service";
import {UserUpdateInputDto} from "./user.update.input.dto";
import {AuthorDto} from "./author.dto";

/**
 * Effectue des appels HTTP à l'API REST /api/users pour gérer les fonctionnalités des users.
 */
@Injectable()
export class UserService {
  private readonly baseUrl = '/api/users';

  constructor(private httpClient: HttpClient, private authService: AuthService) {}

  public findMe(): Observable<UserDto>{
    return this.authService.me().pipe(
      switchMap(value => of(value.user))
    )
  }

  public findUser(id: string): Observable<UserDto> {
    console.log(`Je lance un GET sur ${this.baseUrl}/${id}`);
    return this.httpClient.get<UserDto>(`${this.baseUrl}/${id}`)
      .pipe(
        tap({
          next: user => console.log(`J'ai trouvé l'utilisateur ${user.username}`),
          error: err => console.log(`Problème en faisant un GET de l'utilisateur ${id}`, err)
        })
      );
  }

  public findAuthor(id: string): Observable<AuthorDto> {
    console.log(`Je lance un GET sur ${this.baseUrl}/author/${id}`);
    return this.httpClient.get<AuthorDto>(`${this.baseUrl}/author/${id}`)
      .pipe(
        tap({
          next: user => console.log(`J'ai trouvé l'utilisateur ${user.username} sous forme d'auteur`),
          error: err => console.log(`Problème en faisant un GET de l'auteur ${id}`, err)
        })
      );
  }

  public update(id: string, input: UserUpdateInputDto): Observable<UserDto> {
    console.log(`Je lance un PUT sur ${this.baseUrl}/${id}`);
    return this.httpClient.put<UserDto>(`${this.baseUrl}/${id}`, input)
      .pipe(
        tap({
          next: user => console.log(`J'ai modifié l'utilisateur ${user.id}`),
          error: err => console.log(`Problème en faisant un PUT de l'utilisateur ${id}`, err)
        })
      );
  }

  public delete(id: string): Observable<void> {
    console.log(`Je lance un DELETE sur ${this.baseUrl}/${id}`);
    return this.httpClient.delete<void>(`${this.baseUrl}/${id}`)
      .pipe(
        tap({
          next: () => console.log(`J'ai supprimé l'utilisateur ${id}`),
          error: err => console.log(`Problème en faisant un DELETE de l'utilisateur ${id}`, err)
        })
      );
  }

}
