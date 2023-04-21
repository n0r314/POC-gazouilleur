import {Component, OnInit} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TweetService } from '../tweet.service';
import {TweetDto} from "../tweet.dto";
import {map, switchMap} from "rxjs";

/**
 * Composant qui représente la page détail d'un tweet.
 */
@Component({
  selector: 'app-tweet-detail',
  template: `
    <app-tweet-card [tweet]="tweet" ></app-tweet-card>

    <app-tweet-comment-list></app-tweet-comment-list>

  `,
  styleUrls: ['./tweet-detail.component.scss'],
})
export class TweetDetailComponent implements OnInit {

  id: string | null | undefined;
  tweet: TweetDto | undefined;


  constructor(
    private route: ActivatedRoute,
    private tweetService: TweetService
  ) {
  }

  ngOnInit(): void {
    // on récupère l'id dans l'url pour trouver le bon article
    this.route.paramMap
      .pipe(
        // je récupère l'id depuis les params de l'URL
        map((params) => params.get("tweetId")),
        // je trouve l'article correspondant
        switchMap((id) => {
          this.id = id;
          return this.tweetService.findOne(this.id!);
        })
      )
      .subscribe((tweet) => {
        this.tweet = tweet;
      });
  }
}
