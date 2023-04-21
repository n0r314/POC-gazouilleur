package com.zenika.academy.gazouilleur.model.comment;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.UUID;

public class CommentInputDto {

  @NotBlank
  @Size(max=255)
  private String content;

  public CommentInputDto() {
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

}
