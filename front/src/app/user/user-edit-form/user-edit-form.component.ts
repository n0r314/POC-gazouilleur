import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../user.service';
import {map, switchMap} from "rxjs";
import {UserUpdateInputDto} from "../user.update.input.dto";

/**
 * Composant qui représente la page d'édition d'un utilisateur.
 */
@Component({
  selector: 'app-user-edit-form',
  template: `
    <div *ngIf="successOn" class="message success">
      Modification réussie
    </div>

    <app-avatar *ngIf="user; else loader" [width]="128" [height]="128" [src]="user.avatar!" class="avatar"></app-avatar>
    <form *ngIf="user; else loader" (ngSubmit)="onSubmit()" ngNativeValidate>
      <p>@{{username}}</p>
      <input [(ngModel)]="user.nickname" class="input" type="text" name="nickname" placeholder="John Doe"
             maxlength="64"
             required/>
      <input [(ngModel)]="user.avatar" class="input" type="url" name="avatar"
             placeholder="https://miro.medium.com/max/720/1*W35QUSvGpcLuxPo3SRTH4w.png"/>
      <textarea [(ngModel)]="user.description" class="input" name="description" placeholder="Description..."></textarea>

      <div class="buttons">
        <button class="btn" type="submit">Modifier</button>
      </div>
    </form>
    <ng-template #loader>
      <div class="loader">Chargement...</div>
    </ng-template>
  `,
  styleUrls: ['./user-edit-form.component.scss'],
})

export class UserEditFormComponent implements OnInit {
  id: string | null | undefined;
  user: UserUpdateInputDto | undefined;
  username: string | undefined;
  successOn: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    // on récupère l'id dans l'url
    this.route.paramMap
      .pipe(
        // je récupère l'id depuis les params de l'URL
        map((params) => params.get("userId")),
        // je trouve l'utilisateur correspondant
        switchMap((id) => {
          this.id = id;
          return this.userService.findUser(this.id!);
        })
      )
      .subscribe(({id, username, password, ...user}) => {
        this.username = username;
        this.user = user;
      });
  }

  onSubmit() {
    this.userService.update(this.id!, this.user!).subscribe();

    setTimeout(
      () => {
        this.successOn = true;
        setTimeout(
          () => this.router.navigateByUrl("users/"+String(this.id!)).then(),
          1000
        )
      },
      500
    )
  }
}
