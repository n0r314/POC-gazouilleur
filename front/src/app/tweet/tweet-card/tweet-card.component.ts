import {AfterViewInit, Component, Input, OnInit} from '@angular/core';
import {TweetDto} from "../tweet.dto";
import {AuthOutputDto} from "../../auth/auth.output.dto";
import {AuthService} from "../../auth/auth.service";
import {TweetService} from "../tweet.service";
import {Router} from "@angular/router";

/**
 * Composant qui représente un tweet.
 */
@Component({
  selector: 'app-tweet-card',
  template: `
    <div *ngIf="tweet; else loader" class="card">
      <div class="author">
        <app-avatar [width]="40" [height]="40" [src]="tweet.author.avatar"></app-avatar>
        <div class="title">
          <div class="nickname">
            <a routerLink="/users/{{tweet.author.id}}"> {{tweet.author.nickname}} </a>
            <a class="secondary" routerLink="/users/{{tweet.author.id}}">@{{tweet.author.username}}</a>
          </div>
          <div class="secondary">{{tweet.createdAt | date : "'Le 'dd/MM/YYYY' à 'HH'h'mm"}} </div>
        </div>
      </div>
      <div class="content">{{tweet.content}}</div>
      <div class="buttons">
        <button type="button" class="btn">Likes (0)</button>
        <a *ngIf="!this.router.url.includes('tweets/')" routerLink="/tweets/{{tweet.id}}" class="btn" routerLinkActive="active"
        >Commentaires ({{nbComments}})</a>
      </div>
      <div *ngIf="authorIsMe" class="buttons">
        <button class="btn delete" (click)="onDelete()">Supprimer</button>
        <a class="btn edit" [routerLink]="this.tweet.id+'/edit'">Editer</a>
      </div>
    </div>
    <ng-template #loader>
      <div class="loader">Chargement...</div>
    </ng-template>
  `,
  styleUrls: ['./tweet-card.component.scss'],
})
export class TweetCardComponent implements OnInit, AfterViewInit {
  @Input() tweet: TweetDto | undefined ;
  me: AuthOutputDto | undefined;
  authorIsMe: boolean | undefined;
  nbComments: number = 0;

  constructor(private authService: AuthService, private tweetService: TweetService, public router: Router) {
  }

  ngOnInit(): void {
    // on recherche qui est authentifié
    this.authService.me().subscribe({
      next: value => {
        if (value.user !=null) {
          this.me = value;
          this.authorIsMe = this.me!.user.username == this.tweet!.author.username;
        }
      }
    });
  }

  ngAfterViewInit(): void {
    //debugger
    // on recherche le nombre de commentaires
    this.tweetService.nbComments(this.tweet!.id).subscribe({
      next: value => {
        this.nbComments = value;
      }
    });
  }

  onDelete() {
    if (confirm("Etes-vous certain.e de vouloir supprimer ce gazouilli ?")) {
      this.tweetService.delete(this.tweet!.id).subscribe();
    }
    window.location.reload();
  }



}
