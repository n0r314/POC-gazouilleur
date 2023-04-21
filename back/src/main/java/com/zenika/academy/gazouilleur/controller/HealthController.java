package com.zenika.academy.gazouilleur.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/health")
public class HealthController {

  @ResponseStatus(code = HttpStatus.OK)
  @GetMapping
  public ResponseEntity<String> getUsers() {
    return ResponseEntity.ok("tout va bien pour le moment !");
  }

}
