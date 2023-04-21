import { Component, OnInit } from '@angular/core';
import {NavigationStart, Router} from '@angular/router';
import {AuthService} from "../../auth/auth.service";
import {AuthOutputDto} from "../../auth/auth.output.dto";
import {filter, switchMap} from "rxjs";

/**
 * Composant header de l'application.
 */
@Component({
  selector: 'app-header',
  template: `
    <header>
      <div class="header-left">
        <a routerLink="/">
          <img src="/assets/gazouilli.png" alt="logo gazouilleur"/>
        </a>
        <form (submit)="onSearch($event, search.value)">
          <input #search type="text" class="input" placeholder="# Explore" />
        </form>
      </div>
      <div *ngIf="this.me" class="header-right">
        <app-avatar [src]="me!.user.avatar"></app-avatar>
        <a routerLink="/users/{{me!.user.id}}"> {{me!.user.nickname}} </a>
        <button (click)="onLogout()">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 26 26"
            width="20px"
            height="20px"
          >
            <g id="surface198914343">
              <path
                fill="var(--text)"
                d="M 21.734375 19.640625 L 19.636719 21.734375 C 19.253906 22.121094 18.628906 22.121094 18.242188 21.734375 L 13 16.496094 L 7.761719 21.734375 C 7.375 22.121094 6.746094 22.121094 6.363281 21.734375 L 4.265625 19.640625 C 3.878906 19.253906 3.878906 18.628906 4.265625 18.242188 L 9.503906 13 L 4.265625 7.761719 C 3.882812 7.371094 3.882812 6.742188 4.265625 6.363281 L 6.363281 4.265625 C 6.746094 3.878906 7.375 3.878906 7.761719 4.265625 L 13 9.507812 L 18.242188 4.265625 C 18.628906 3.878906 19.257812 3.878906 19.636719 4.265625 L 21.734375 6.359375 C 22.121094 6.746094 22.121094 7.375 21.738281 7.761719 L 16.496094 13 L 21.734375 18.242188 C 22.121094 18.628906 22.121094 19.253906 21.734375 19.640625 Z M 21.734375 19.640625 "
              />
            </g>
          </svg>
        </button>
      </div>
      <div *ngIf="!this.me" class="header-right-login" >
        <a routerLink="/login" class="btn"> Se connecter </a>
      </div>
    </header>
  `,
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {

  me: AuthOutputDto | undefined;

  constructor(private router: Router, private authService: AuthService) {
  }

  ngOnInit(): void {
    this.router.events.pipe(
      //tap(event => console.log(event)),
      filter(value => value instanceof NavigationStart),
      switchMap(() => this.authService.me())
    ).subscribe({
      next: value => {
        if (value.user != null) {
          this.me = value
        }
      }
    })
  }

  onSearch(e: SubmitEvent, search: string) {
    e.preventDefault();
    const queryParams = search ? { search } : {};
    void this.router.navigate([''], { queryParams });
  }

  onLogout() {
    this.authService.logout();
    void this.router.navigate(['/login']);
    window.location.reload()
  }
}
