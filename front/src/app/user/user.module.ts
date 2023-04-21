import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { TweetModule } from '../tweet/tweet.module';
import { UserCardComponent } from './user-card/user-card.component';
import { UserEditFormComponent } from './user-edit-form/user-edit-form.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { UserRoutingModule } from './user-routing.module';
import { UserService } from './user.service';

/**
 * Module des tweets :
 * - Carte d'un utilisateur (UserCardComponent)
 * - Page de profil d'un utilisateur (UserProfileComponent)
 * - Page éditer un utilisateur (UserEditFormComponent)
 */
@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,
    UserRoutingModule,
    SharedModule,
    TweetModule,
  ],
  providers: [UserService],
  declarations: [
    UserCardComponent,
    UserEditFormComponent,
    UserProfileComponent,
  ],
})
export class UserModule {}
