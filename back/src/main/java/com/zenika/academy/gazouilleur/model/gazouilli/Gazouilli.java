package com.zenika.academy.gazouilleur.model.gazouilli;

import com.zenika.academy.gazouilleur.model.user.User;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "gazouillis")
public class Gazouilli {

  @Id
  @GeneratedValue
  private UUID id;
  private String content;

  //@Column(columnDefinition = "created_at timestamp with time zone not null default now()")
  private Instant createdAt;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "author_id", nullable = false)
  private User author;


  public Gazouilli() {
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

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }
}
