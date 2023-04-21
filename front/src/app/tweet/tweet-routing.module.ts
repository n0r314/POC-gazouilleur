import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TweetDetailComponent } from './tweet-detail/tweet-detail.component';
import { TweetListComponent } from './tweet-list/tweet-list.component';
import {TweetEditFormComponent} from "./tweet-edit-form/tweet-edit-form.component";

const routes: Routes = [
  // page liste des tweets
  { path: '', component: TweetListComponent },
  // page détail d'un tweet
  { path: ':tweetId', component: TweetDetailComponent },
  // pour modifier un tweet
  { path: ':tweetId/edit', component: TweetEditFormComponent },
];

/**
 * Routing des pages liste des tweets & détail d'un tweet.
 */
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TweetRoutingModule {}
