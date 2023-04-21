package com.zenika.academy.gazouilleur.model.gazouilli;

import com.zenika.academy.gazouilleur.model.user.AuthorOutputDto;

import java.time.Instant;
import java.util.UUID;


public class GazouilliOutputDto {

  private UUID id;
  private String content;
  private Instant createdAt;
  private AuthorOutputDto author;

  public GazouilliOutputDto() {

  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
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

  public AuthorOutputDto getAuthor() {
    return author;
  }

  public void setAuthor(AuthorOutputDto author) {
    this.author = author;
  }
}
