package com.zenika.academy.gazouilleur.model.auth;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AuthInputDto {

  @NotBlank
  @Size(max=64)
  @Pattern(regexp = "[a-z0-9]+", message = "Username must contain only letters or numbers (case insensitive)")
  private String username;

  @NotBlank
  @Pattern(regexp = "[a-zA-Z0-9]+", message = "Username must contain only letters or numbers (case sensitive)")
  private String password;

  public AuthInputDto() {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
