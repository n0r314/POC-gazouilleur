import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserEditFormComponent } from './user-edit-form/user-edit-form.component';
import { UserProfileComponent } from './user-profile/user-profile.component';

const routes: Routes = [
  // page de profil d'un utilisateur
  { path: ':userId', component: UserProfileComponent },
  // page d'édition d'un utilisateur
  { path: ':userId/edit', component: UserEditFormComponent },
  // autrement, rediriger vers les tweets
  { path: '', redirectTo: '/tweets', pathMatch: 'full' },
];

/**
 * Routing des pages profil & édition d'un utilisateur.
 */
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserRoutingModule {}
