import { Component } from '@angular/core';
import {AuthService, RegisterInputDto} from '../auth.service';
import {Router} from "@angular/router";

/**
 * Composant qui représente la page d'inscription.
 */
@Component({
  selector: 'app-register-form',
  template: `
    <div *ngIf="successOn" class="message success">
      Bonjour {{user.nickname}} ! Compte crée. Vous êtes maintenant authentifié :)
    </div>
    <form (ngSubmit)="onSubmit()" ngNativeValidate>
      <input [(ngModel)]="user.nickname" class="input" type="text" name="nickname" placeholder="John Doe" maxlength="64" required/>
      <input [(ngModel)]="user.username" class="input" type="text" name="username" placeholder="@johndoe" pattern="[a-z0-9]+" maxlength="64" required/>
      <input [(ngModel)]="user.password" class="input" type="password" name="password" placeholder="mot de passe" pattern="[a-zA-Z0-9]+" required/>
      <div class="buttons">
        <button class="btn" type="submit">S'inscrire</button>
      </div>
    </form>
  `,
  styleUrls: ['./register-form.component.scss'],
})
export class RegisterFormComponent {

  user: RegisterInputDto = {
    username: "",
    password: "",
    nickname: ""
  };
  successOn: boolean = false;

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    this.authService.register(this.user).subscribe();

    setTimeout(
      () => {
        this.successOn = true;
          setTimeout(
            () =>  {
              this.router.navigateByUrl("/").then(() =>  window.location.reload());
            },
            1500
          )
      },
      500
    )
  }
}
