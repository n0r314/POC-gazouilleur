package com.zenika.academy.gazouilleur.model.gazouilli;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.UUID;


public class GazouilliInputDto {
  @NotBlank
  @Size(max=255)
  private String content;

  public GazouilliInputDto() {
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

}
