import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginFormComponent } from './login-form/login-form.component';
import { RegisterFormComponent } from './register-form/register-form.component';

const routes: Routes = [
  // page de connexion
  { path: 'login', component: LoginFormComponent },
  // page d'inscription
  { path: 'register', component: RegisterFormComponent },
];

/**
 * Routing des pages de connexion et d'inscription.
 */
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AuthRoutingModule {}
