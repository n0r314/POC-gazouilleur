import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AvatarComponent } from './avatar/avatar.component';
import { ContentComponent } from './content/content.component';
import { HeaderComponent } from './header/header.component';

/**
 * Module partagé :
 * - Header de l'application (HeaderComponent)
 * - Contenu de l'application (ContentComponent)
 * - Avatar d'un utilisateur (AvatarComponent)
 */
@NgModule({
  imports: [CommonModule, RouterModule],
  declarations: [HeaderComponent, ContentComponent, AvatarComponent],
  exports: [HeaderComponent, ContentComponent, AvatarComponent],
})
export class SharedModule {}
