import {Component} from '@angular/core';
import {AuthService, Credentials} from '../auth.service';
import {Router} from "@angular/router";

/**
 * Composant qui représente la page de connexion.
 */
@Component({
  selector: 'app-login-form',
  template: `
    <div *ngIf="successOn" class="message success">
      Bonjour @{{credentials.username}} ! Identification réussie :)
    </div>
    <div *ngIf="failureOn" class="message failure">
      L'utilisateur @{{credentials.username}} n'est pas connu avec ce mot de passe
    </div>
    <form (ngSubmit)="onSubmit()" ngNativeValidate>
      <input class="input" type="text" name="username" placeholder="@johndoe" pattern="[a-z0-9]+" maxlength="64" required
             [(ngModel)]="credentials.username"/>
      <input class="input" type="password" name="password" placeholder="mot de passe" pattern="[a-zA-Z0-9]+" required
             [(ngModel)]="credentials.password"/>
      <div class="buttons">
        <a routerLink="/register" class="btn">S'inscrire</a>
        <button class="btn" type="submit">Se connecter</button>
      </div>
    </form>
  `,
  styleUrls: ['./login-form.component.scss'],
})
export class LoginFormComponent {

  credentials: Credentials = {
    username: "",
    password: ""
  };
  successOn: boolean = false;
  failureOn: boolean = false;

  constructor(private router: Router, private authService: AuthService) {
  }

  onSubmit() {
    this.authService.login(this.credentials).subscribe();

    setTimeout(
      () => {

        if (this.authService.isLoggedIn()) {
          this.successOn = true;
          setTimeout(
            () => {
              this.router.navigateByUrl("tweets").then(() => window.location.reload());
            },
            1000
          )
        } else {
          this.failureOn = true;
          setTimeout(
            () => window.location.reload(),
            1000
          )
        }

      },

      1000
    )
  }


}
