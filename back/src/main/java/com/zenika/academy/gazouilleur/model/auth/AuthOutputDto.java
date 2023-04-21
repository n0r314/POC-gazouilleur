package com.zenika.academy.gazouilleur.model.auth;

import com.zenika.academy.gazouilleur.model.user.UserOutputDto;

public class AuthOutputDto {

  private UserOutputDto user;
  private String token;

  public AuthOutputDto() {
  }

  public UserOutputDto getUser() {
    return user;
  }

  public void setUser(UserOutputDto user) {
    this.user = user;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
