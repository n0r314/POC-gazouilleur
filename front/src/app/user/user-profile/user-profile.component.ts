import {Component, OnInit} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../user.service';
import {map, switchMap} from "rxjs";
import {AuthorDto} from "../author.dto";
import {TweetDto} from "../../tweet/tweet.dto";
import {TweetService} from "../../tweet/tweet.service";

/**
 * Composant qui représente la page de profil d'un utilisateur.
 */
@Component({
  selector: 'app-user-profile',
  template: `
    <app-user-card [author]="this.author"></app-user-card>

    <div *ngIf="tweetList; else loading">
      <ng-container *ngFor="let tweet of tweetList">
        <app-tweet-card [tweet]="tweet" class="card"></app-tweet-card>
      </ng-container>
    </div>

    <ng-template #loading>
      <div>Chargement...</div>
    </ng-template>
  `,
  styleUrls: ['./user-profile.component.scss'],
})
export class UserProfileComponent implements OnInit {

  id: string | null | undefined;
  author: AuthorDto | undefined;
  tweetList: TweetDto[] | undefined;


  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private tweetService: TweetService
  ) {}

  ngOnInit(): void {
    // on récupère l'id dans l'url pour trouver le bon utilisateur
    this.route.paramMap
      .pipe(
        // je récupère l'id depuis les params de l'URL
        map((params) => params.get("userId")),
        // je trouve l'utilisateur correspondant
        switchMap((id) => {
          this.id = id;
          return this.userService.findAuthor(this.id!);
        })
      )
      .subscribe((user) => {
        this.author = user;
        // on récupère la liste des tweets de l'auteur
        const params = {authorname : this.author.username};
        this.tweetService.findAll(params).subscribe({
          next: value => this.tweetList = value,
          complete: () => console.log("GET des gazouillis avec filtre par auteur terminé")
        })
      });
  }
}
