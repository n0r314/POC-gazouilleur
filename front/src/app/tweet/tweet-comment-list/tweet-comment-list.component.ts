import {Component, OnInit} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {TweetService} from '../tweet.service';
import {CommentDto} from "../comment.dto";
import {map, switchMap, tap} from "rxjs";
import {AuthService} from "../../auth/auth.service";

/**
 * Composant qui représente la liste des commentaires d'un tweet.
 */
@Component({
  selector: 'app-tweet-comment-list',
  template: `
    <app-tweet-comment-form *ngIf="tweetId && this.authService.isLoggedIn()" [tweetId]="tweetId"></app-tweet-comment-form>

    <div *ngIf="commentList && tweetId; else loading">
      <ng-container *ngFor="let comment of commentList">
        <app-tweet-comment-card [tweetId]="tweetId" [comment]="comment" class="card"></app-tweet-comment-card>
      </ng-container>
    </div>

    <ng-template #loading>
      <div>Chargement...</div>
    </ng-template>
  `,
  styleUrls: ['./tweet-comment-list.component.scss'],
})
export class TweetCommentListComponent implements OnInit {
  commentList: CommentDto[] | undefined;
  tweetId: string | null | undefined;


  constructor(
    private route: ActivatedRoute,
    private tweetService: TweetService,
    public readonly authService: AuthService
  ) {}


  ngOnInit(): void {
    // on récupère l'id du gazouilli dans l'url
    this.route.paramMap
      .pipe(
        // je récupère l'id depuis les params de l'URL
        map((params) => params.get("tweetId")),
        tap({
          // on garde l'id sous le coude
          next: tweetId => this.tweetId = tweetId
        }),
        // je retourne l'observable de recherche des commentaires de ce tweet
        switchMap((id) => this.tweetService.findComments(id!))
      )
      .subscribe({
        next: value => this.commentList = value,
        complete: () => console.log("GET des commentaires terminé")
      });
  }

}
