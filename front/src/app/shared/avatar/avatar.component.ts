import { Component, Input } from '@angular/core';

/**
 * Composant qui représente l'avatar d'un utilisateur.
 */
@Component({
  selector: 'app-avatar',
  template: ` <img [src]="src" [width]="width" [height]="height" /> `,
  styleUrls: ['./avatar.component.scss'],
})
export class AvatarComponent {
  @Input() src =
    'https://png.pngtree.com/png-vector/20190704/ourlarge/pngtree-businessman-user-avatar-free-vector-png-image_1538405.jpg';
  @Input() width = 32;
  @Input() height = 32;
}
