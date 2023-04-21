import { Component } from '@angular/core';

/**
 * Composant qui englobe le contenu de chaque page.
 */
@Component({
  selector: 'app-content',
  template: ` <ng-content></ng-content>
  <a class="credits" href="https://www.flaticon.com/fr/icones-gratuites/oiseau" title="oiseau icônes">Logo créé par Freepik - Flaticon</a>`,
  styleUrls: ['./content.component.scss'],
})
export class ContentComponent {}
