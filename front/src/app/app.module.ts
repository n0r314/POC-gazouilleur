import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthModule } from './auth/auth.module';
import { SharedModule } from './shared/shared.module';
import { TweetModule } from './tweet/tweet.module';
import { UserModule } from './user/user.module';

@NgModule({
  imports: [
    BrowserModule,
    AppRoutingModule,
    AuthModule,
    SharedModule,
    TweetModule,
    UserModule,
  ],
  providers: [],
  declarations: [AppComponent],
  bootstrap: [AppComponent],
})
export class AppModule {}
