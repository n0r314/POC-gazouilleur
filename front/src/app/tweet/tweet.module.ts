import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { TweetCardComponent } from './tweet-card/tweet-card.component';
import { TweetCommentCardComponent } from './tweet-comment-card/tweet-comment-card.component';
import { TweetCommentFormComponent } from './tweet-comment-form/tweet-comment-form.component';
import { TweetCommentListComponent } from './tweet-comment-list/tweet-comment-list.component';
import { TweetDetailComponent } from './tweet-detail/tweet-detail.component';
import { TweetFormComponent } from './tweet-form/tweet-form.component';
import { TweetListComponent } from './tweet-list/tweet-list.component';
import { TweetRoutingModule } from './tweet-routing.module';
import { TweetService } from './tweet.service';
import { TweetEditFormComponent } from './tweet-edit-form/tweet-edit-form.component';

/**
 * Module des tweets :
 * - Carte d'un tweet (TweetCardComponent)
 * - Page liste des tweets (TweetListComponent)
 * - Page détail d'un tweet (TweetDetailComponent)
 * - Carte pour poster un nouveau tweet (TweetFormComponent)
 * - Carte liste des commentaires d'un tweet (TweetCommentListComponent)
 * - Carte d'un commentaire d'un tweet (TweetCommentCardComponent)
 * - Carte pour poster un commentaire d'un tweet (TweetCommentFormComponent)
 */
@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,
    SharedModule,
    TweetRoutingModule,
  ],
  providers: [TweetService],
  declarations: [
    TweetCardComponent,
    TweetCommentListComponent,
    TweetDetailComponent,
    TweetFormComponent,
    TweetListComponent,
    TweetCommentCardComponent,
    TweetCommentFormComponent,
    TweetEditFormComponent,
  ],
  exports: [TweetCardComponent],
})
export class TweetModule {}
