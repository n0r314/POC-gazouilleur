import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  // pages du module des tweets
  {
    path: 'tweets',
    loadChildren: () =>
      import('./tweet/tweet.module').then((m) => m.TweetModule),
  },
  // pages du module des utilisateurs
  {
    path: 'users',
    loadChildren: () => import('./user/user.module').then((m) => m.UserModule),
  },
  // la page d'accueil redirige vers la page liste des tweets
  {
    path: '',
    redirectTo: 'tweets',
    pathMatch: 'full',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
