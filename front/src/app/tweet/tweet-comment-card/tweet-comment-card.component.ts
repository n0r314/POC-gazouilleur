import {Component, Input, OnInit} from '@angular/core';
import {CommentDto} from "../comment.dto";
import {AuthService} from "../../auth/auth.service";
import {TweetService} from "../tweet.service";

/**
 * Composant qui représente la un commentaire d'un tweet.
 */
@Component({
  selector: 'app-tweet-comment-card',
  template: `
    <div *ngIf="comment; else loader">
      <div class="title">
        <div class="author">
          <app-avatar [width]="28" [height]="28" [src]="comment.author.avatar"></app-avatar>
          <a routerLink="/users/{{comment.author.id}}"> {{comment.author.nickname}} </a>
          <a class="secondary" routerLink="/users/{{comment.author.id}}">@{{comment.author.username}}</a>
        </div>
        <p class="secondary">{{comment.createdAt | date : "'Le 'dd/MM/YYYY' à 'HH'h'mm"}} </p>
      </div>
      <div class="content">{{comment.content}}</div>

      <div *ngIf="authorIsMe" class="buttons">
        <button class="btn delete" (click)="onDelete()">Supprimer</button>
      </div>
    </div>
    <ng-template #loader>
      <div class="loader">Chargement...</div>
    </ng-template>
  `,
  styleUrls: ['./tweet-comment-card.component.scss'],
})
export class TweetCommentCardComponent implements OnInit {
  @Input() comment: CommentDto | undefined;
  @Input() tweetId: string | undefined;
  authorIsMe: boolean | undefined;

  constructor(private authService: AuthService, private tweetService: TweetService) {
  }

  ngOnInit(): void {
    this.authService.me().subscribe({
      next: value => {
        if (value.user != null) {
          this.authorIsMe = value.user.username == this.comment!.author.username;
        }
      }
    });
  }


  onDelete() {
    if (confirm("Etes-vous certain.e de vouloir supprimer ce commentaire ?")) {
      this.tweetService.deleteComment(this.tweetId!, this.comment!.id).subscribe();
    }
    window.location.reload();
  }

}
