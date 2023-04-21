import {Component, OnInit} from '@angular/core';
import {TweetDto} from "../tweet.dto";
import {map, switchMap} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {TweetService} from "../tweet.service";


/**
 * Composant qui représente la page d'édition d'un gazouilli
 */
@Component({
  selector: 'app-tweet-edit-form',
  template: `
    <div *ngIf="successOn" class="message success">
      Modification réussie
    </div>

    <div *ngIf="tweet; else loader" class="card">
      <div class="user">
        <app-avatar [width]="80" [height]="80" [src]="tweet.author.avatar"></app-avatar>
        <span>{{tweet.author.nickname}}</span>
        <span class="secondary">@{{tweet.author.username}}</span>
      </div>

      <form ngNativeValidate (ngSubmit)="onSubmit()">
        <div class="inputs">
          <textarea
            class="input"
            name="content"
            placeholder="Quoi de neuf ?"
            maxlength="255"
            required
            [(ngModel)]="tweet.content"
          ></textarea>
        </div>
        <div class="buttons">
          <button type="submit" class="btn edit">Modifier</button>
        </div>
      </form>
    </div>
    <ng-template #loader>
      <div class="loader">Chargement...</div>
    </ng-template>
  `,
  styleUrls: ['./tweet-edit-form.component.scss']
})
export class TweetEditFormComponent implements OnInit {
  tweet: TweetDto | undefined;
  id: string | null | undefined;
  successOn: boolean = false;


  constructor(private route: ActivatedRoute, private tweetService: TweetService, private router: Router) {
  }

  ngOnInit(): void {
    // on récupère l'id dans l'url
    this.route.paramMap
      .pipe(
        // je récupère l'id depuis les params de l'URL
        map((params) => params.get("tweetId")),
        // je trouve l'utilisateur correspondant
        switchMap((id) => {
          this.id = id;
          return this.tweetService.findOne(this.id!);
        })
      )
      .subscribe((tweet) => {
        this.tweet = tweet;
      });
  }

  onSubmit() {
    this.tweetService.update(this.id!, this.tweet!).subscribe();

    setTimeout(
      () => {
        this.successOn = true;
        setTimeout(
          () => this.router.navigateByUrl("tweets").then(),
          1000
        )
      },
      500
    )
  }

}
