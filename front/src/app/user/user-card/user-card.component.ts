import {Component, Input, OnInit} from '@angular/core';
import {UserService} from "../user.service";
import {ActivatedRoute, Router} from "@angular/router";
import {map} from "rxjs";
import {AuthService} from "../../auth/auth.service";
import {AuthorDto} from "../author.dto";
import {AuthOutputDto} from "../../auth/auth.output.dto";

/**
 * Composant qui représente un utilisateur.
 */
@Component({
  selector: 'app-user-card',
  template: `

    <div *ngIf="successOn" class="message success">
      Suppression réussie
    </div>

    <div class="card">
      <div *ngIf="author; else loader" class="user-card">
        <app-avatar [width]="80" [height]="80" [src]="author.avatar"></app-avatar>
        <div class="username">
          <span>{{author.nickname}}</span>
          <span class="secondary">@{{author.username}}</span>
        </div>
        <span>{{author.description}}</span>
        <div class="buttons">
          <button *ngIf="authorIsMe" class="btn delete" (click)="onDelete()">Supprimer</button>
          <a *ngIf="authorIsMe" class="btn edit" routerLink="edit">Editer</a>
          <button class="btn">Suivre (0)</button>
        </div>
      </div>
    </div>
    <ng-template #loader>
      <div class="loader">Chargement...</div>
    </ng-template>
  `,
  styleUrls: ['./user-card.component.scss'],
})
export class UserCardComponent implements OnInit {
  @Input() author: AuthorDto | undefined;
  id: string | null | undefined;
  successOn: boolean = false;
  me: AuthOutputDto | undefined;
  authorIsMe: boolean | undefined;


  constructor(private userService: UserService, private route: ActivatedRoute, private router: Router, private authService: AuthService) {
  }


  ngOnInit(): void {
    // on récupère l'id dans l'url
    this.route.paramMap
      .pipe(
        // je récupère l'id depuis les params de l'URL
        map((params) => params.get("userId")),
      )
      .subscribe(id => {
        this.id = id;
        this.authService.me().subscribe({
            next: value => {
              this.me = value;
              this.authorIsMe = this.me!.user.username == this.author!.username;
            }
          });
      });
  }



  onDelete() {
    if (confirm("Etes-vous certain.e de vouloir supprimer votre compte et toutes les données associées ?")) {
      this.userService.delete(this.id!).subscribe();
    }


    setTimeout(
      () => {
        this.successOn = true;
        this.authService.logout();
        setTimeout(
          () => {
            this.router.navigateByUrl("/").then(() => window.location.reload());
          },
          1000
        )
      },
      500
    )
  }



}
