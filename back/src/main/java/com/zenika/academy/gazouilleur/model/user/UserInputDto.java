package com.zenika.academy.gazouilleur.model.user;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;



public class UserInputDto {

  @NotBlank
  @Size(max=64)
  @Pattern(regexp = "[a-z0-9]+", message = "Username must contain only letters or numbers (case insensitive)")
  private String username;

  @NotBlank
  private String password;

  @NotBlank
  @Size(max=64)
  private String nickname;

  @URL
  private String avatar;

  private String description;

  public UserInputDto() {
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

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
