package com.zenika.academy.gazouilleur.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

  private final UUID id;
  private final Class<?> entityClass;



  public NotFoundException(Class<?> entityClass, UUID id) {
    super(entityClass.getSimpleName() + " with identifier " + id + " is not found");
    this.id = id;
    this.entityClass = entityClass;
  }

  public NotFoundException(Class<?> entityClass, UUID id, Throwable cause) {
    super(entityClass.getSimpleName() + " with identifier " + id + " is not found", cause);
    this.id = id;
    this.entityClass = entityClass;
  }

  public UUID getId() {
    return id;
  }

  public Class<?> getEntityClass() {
    return entityClass;
  }
}
