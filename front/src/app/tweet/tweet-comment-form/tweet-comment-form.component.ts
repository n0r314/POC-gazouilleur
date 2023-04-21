import {Component, Input, OnInit} from '@angular/core';
import {AuthOutputDto} from "../../auth/auth.output.dto";
import {TweetService} from "../tweet.service";
import {AuthService} from "../../auth/auth.service";
import {CommentInputDto} from "../comment.input.dto";

/**
 * Composant qui permet de poster un nouveau commentaire sur un tweet.
 */
@Component({
  selector: 'app-tweet-comment-form',
  template: `
    <div *ngIf="registeredUser" class="card">
      <form ngNativeValidate (ngSubmit)="onSubmit()">
        <div class="inputs">
          <app-avatar [width]="56" [height]="56" [src]="registeredUser.user.avatar"></app-avatar>
          <textarea
            class="input"
            name="content"
            placeholder="Un commentaire {{registeredUser.user.nickname}} ?"
            maxlength="255"
            required
            [(ngModel)]="comment.content"
          ></textarea>
        </div>
        <div class="buttons">
          <button type="submit" class="btn">Commenter</button>
        </div>
      </form>
    </div>
  `,
  styleUrls: ['./tweet-comment-form.component.scss'],
})
export class TweetCommentFormComponent implements OnInit {
  @Input() tweetId: string | undefined;
  comment: CommentInputDto = {
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

  constructor(private tweetService: TweetService, public readonly authService: AuthService) {
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
    this.tweetService.createComment(this.tweetId!, this.comment)
      .subscribe({
        next: () => window.location.reload(),
        complete: () => console.log("POST d'un commentaire terminé"),
      });
  }




}
