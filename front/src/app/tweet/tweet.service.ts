import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {map, Observable, tap} from "rxjs";
import {TweetDto} from "./tweet.dto";
import {TweetInputDto} from "./tweet.input.dto";
import {CommentDto} from "./comment.dto";
import {CommentInputDto} from "./comment.input.dto";

/**
 * Effectue des appels HTTP à l'API REST /api/tweets pour gérer les fonctionnalités des tweets.
 */

export interface FindAllGazouillisParams {
  hashtag?: string;
  authorname?: string;
}

@Injectable()
export class TweetService {
  private readonly baseUrl = '/api/gazouillis';

  constructor(private httpClient: HttpClient) {}

  public findAll(filter: FindAllGazouillisParams = {}): Observable<TweetDto[]> {
    console.log(`Je lance un GET sur ${this.baseUrl}`);
    return this.httpClient.get<TweetDto[]>(this.baseUrl, {
      params: {...filter}
    }).pipe(
      tap({
          next: tweets => console.log(`J'ai trouvé ${tweets.length} gazouillis`),
          error: err => console.log(`Problème en faisant un GET sur ${this.baseUrl}`, err)
        }
      )
    );
  }

  public create(input: TweetInputDto): Observable<TweetDto> {
    console.log(`Je lance un POST sur ${this.baseUrl} avec le gazouilli ${input.content}`);
    return this.httpClient.post<TweetDto>(`${this.baseUrl}`, input)
      .pipe(
        tap({
          next: tweet => console.log(`J'ai créé le gazouilli ${tweet.id}`),
          error: err => console.log(`Problème en faisant un POST sur ${this.baseUrl}`, err)
        })
      );
  }

  public findOne(id: string): Observable<TweetDto> {
    console.log(`Je lance un GET sur ${this.baseUrl}/${id}`);
    return this.httpClient.get<TweetDto>(`${this.baseUrl}/${id}`)
      .pipe(
        tap({
          next: tweet => console.log(`J'ai trouvé le gazouilli ${tweet.id}`),
          error: err => console.log(`Problème en faisant un GET du gazouilli ${id}`, err)
        })
      );
  }

  public update(id: string, input: TweetInputDto): Observable<TweetDto> {
    console.log(`Je lance un PUT sur ${this.baseUrl}/${id} avec l'article ${input}`);
    return this.httpClient.put<TweetDto>(`${this.baseUrl}/${id}`, input)
      .pipe(
        tap({
          next: tweet => console.log(`J'ai modifié le gazouilli ${tweet.id}`),
          error: err => console.log(`Problème en faisant un PUT du gazouilli ${id}`, err)
        })
      );
  }

  public delete(id: string): Observable<void> {
    console.log(`Je lance un DELETE sur ${this.baseUrl}/${id}`);
    return this.httpClient.delete<void>(`${this.baseUrl}/${id}`)
      .pipe(
        tap({
          next: () => console.log(`J'ai supprimé le gazouilli ${id}`),
          error: err => console.log(`Problème en faisant un DELETE du gazouilli ${id}`, err)
        })
      );
  }


  ///////////////// commentaires ///////////////
  public findComments(tweetId: string): Observable<CommentDto[]> {
    console.log(`Je lance un GET sur ${this.baseUrl}/${tweetId}/comments`);
    return this.httpClient.get<CommentDto[]>(`${this.baseUrl}/${tweetId}/comments`).pipe(
      tap({
          next: comments => console.log(`J'ai trouvé ${comments.length} commentaires`),
          error: err => console.log(`Problème en faisant un GET sur ${this.baseUrl}/${tweetId}/comments`, err)
        }
      )
    );
  }

  public nbComments(tweetId: string): Observable<number> {
    console.log(`Je lance un GET sur ${this.baseUrl}/${tweetId}/comments`);
    return this.httpClient.get<CommentDto[]>(`${this.baseUrl}/${tweetId}/comments`).pipe(
      tap({
          next: comments => console.log(`J'ai trouvé ${comments.length} commentaires hop hop hop`),
          error: err => console.log(`Problème en faisant un GET sur ${this.baseUrl}/${tweetId}/comments`, err)
        }
      ),
      map(comments => {
        console.log("coucou");
        return comments.length;
      }),
      tap({
        next: nb => console.log(nb)
      })
    );
  }


  public createComment(tweetId: string, input: CommentInputDto): Observable<CommentDto> {
    console.log(`Je lance un POST sur ${this.baseUrl}/${tweetId}/comments`);
    return this.httpClient.post<CommentDto>(`${this.baseUrl}/${tweetId}/comments`, input)
      .pipe(
        tap({
          next: comment => console.log(`J'ai créé le commentaire ${comment.id}`),
          error: err => console.log(`Problème en faisant un POST sur ${this.baseUrl}/${tweetId}/comments`, err)
        })
      );
  }


  public deleteComment(tweetId: string, commentId: string): Observable<void> {
    console.log(`Je lance un DELETE sur ${this.baseUrl}/${tweetId}/comments/${commentId}`);
    return this.httpClient.delete<void>(`${this.baseUrl}/${tweetId}/comments/${commentId}`)
      .pipe(
        tap({
          next: () => console.log(`J'ai supprimé le commentaire ${commentId}`),
          error: err => console.log(`Problème en faisant un DELETE du commentaire $commentId}`, err)
        })
      );
  }
}
