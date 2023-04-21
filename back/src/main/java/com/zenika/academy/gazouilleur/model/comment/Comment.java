package com.zenika.academy.gazouilleur.model.comment;

import com.zenika.academy.gazouilleur.model.gazouilli.Gazouilli;
import com.zenika.academy.gazouilleur.model.user.User;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "comments")
public class Comment {

  @Id
  @GeneratedValue
  private UUID id;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "author_id", nullable = false)
  private User author;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "gazouilli_id", nullable = false)
  private Gazouilli gazouilli;

  private String content;
  private Instant createdAt;

  public Comment() {
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
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

  public Gazouilli getGazouilli() {
    return gazouilli;
  }

  public void setGazouilli(Gazouilli gazouilli) {
    this.gazouilli = gazouilli;
  }
}
