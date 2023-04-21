import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {FindAllGazouillisParams, TweetService} from '../tweet.service';
import {TweetDto} from "../tweet.dto";

/**
 * Composant qui représente la page liste des tweets.
 */
@Component({
  selector: 'app-tweet-list',
  template: `
    <app-tweet-form ></app-tweet-form>

    <div *ngIf="tweetList; else loading">
      <ng-container *ngFor="let tweet of tweetList">
            <app-tweet-card [tweet]="tweet" class="card"></app-tweet-card>
      </ng-container>
    </div>

    <ng-template #loading>
      <div>Chargement...</div>
    </ng-template>
  `,
  styleUrls: ['./tweet-list.component.scss'],
})
export class TweetListComponent implements OnInit {

  tweetList: TweetDto[] | undefined;


  constructor(
    private route: ActivatedRoute,
    private tweetService: TweetService
  ) {
  }

  ngOnInit(): void {
    // récupère les query params (ex: ?search=coucou) dans l'url
    this.route.queryParams.subscribe((params) => {
      console.log(params['search']);
      let myParams: FindAllGazouillisParams = {};
      if (params['search'] != undefined) {
        myParams = {hashtag: "#" + params['search']};
      }
      console.log(myParams);
      this.tweetService.findAll(myParams).subscribe({
        next: value => this.tweetList = value,
        complete: () => console.log("GET des gazouillis terminé")
      })
    });
  }


}
