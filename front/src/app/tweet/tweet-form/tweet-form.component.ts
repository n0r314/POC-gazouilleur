import {Component, OnInit} from '@angular/core';
import {TweetService} from '../tweet.service';
import {TweetInputDto} from "../tweet.input.dto";
import {AuthService} from "../../auth/auth.service";
import {AuthOutputDto} from "../../auth/auth.output.dto";

/**
 * Composant qui permet de poster un nouveau tweet.
 */
@Component({
  selector: 'app-tweet-form',
  template: `
    <div *ngIf="registeredUser.user.username != 'guest'" class="card">
      <form ngNativeValidate (ngSubmit)="onSubmit()">
        <div class="inputs">
          <app-avatar [width]="56" [height]="56" [src]="registeredUser.user.avatar"></app-avatar>
          <textarea
            class="input"
            name="content"
            placeholder="Quoi de neuf {{registeredUser.user.nickname}} ?"
            maxlength="255"
            required
            [(ngModel)]="tweet.content"
          ></textarea>
        </div>
        <div class="buttons">
          <button type="submit" class="btn">Envoyer</button>
        </div>
      </form>
    </div>
  `,
  styleUrls: ['./tweet-form.component.scss'],
})
export class TweetFormComponent implements OnInit {

  tweet: TweetInputDto = {
    content: ""
  };

  registeredUser: AuthOutputDto = {
    user : {
      id : "no_id",
      username : "guest",
      password: "",
      nickname : "Guest",
      avatar : "https://miro.medium.com/max/720/1*W35QUSvGpcLuxPo3SRTH4w.png",
      description : ""
    },
    token : ''
  };


  constructor(private tweetService: TweetService, private authService: AuthService) {
  }

  ngOnInit(): void {
    this.authService.me().subscribe({
      next: value => {
        if (value.user !=null) {
          this.registeredUser = value;
        }
      }
    });
  }

  onSubmit() {
      this.tweetService.create(this.tweet)
        .subscribe({
          next: () => window.location.reload(),
          complete: () => console.log("POST d'un gazouilli terminé"),
        });
    }



}
