package com.zenika.academy.gazouilleur.model.comment;

import com.zenika.academy.gazouilleur.model.user.UserOutputDto;

import java.time.Instant;
import java.util.UUID;


public class CommentOutputDto {

  private UUID id;
  private UserOutputDto author;
  private String content;
  private Instant createdAt;

  public CommentOutputDto() {
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UserOutputDto getAuthor() {
    return author;
  }

  public void setAuthor(UserOutputDto author) {
    this.author = author;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }
}
