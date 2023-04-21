package com.zenika.academy.gazouilleur.controller;

import com.zenika.academy.gazouilleur.model.auth.AuthInputDto;
import com.zenika.academy.gazouilleur.model.auth.AuthOutputDto;
import com.zenika.academy.gazouilleur.model.user.UserInputDto;
import com.zenika.academy.gazouilleur.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService service;

  public AuthController(AuthService service) {
    this.service = service;
  }

  @ResponseStatus(code = HttpStatus.OK)
  @GetMapping("/me")
  public ResponseEntity<AuthOutputDto> getAuth(@RequestHeader(HttpHeaders.AUTHORIZATION) Optional<String> authToken) {
    AuthOutputDto output = new AuthOutputDto();
    if (authToken.isPresent()) {
      output = service.getMe(authToken.get());
    }
    return ResponseEntity.ok(output);
  }

  @ResponseStatus(code = HttpStatus.OK)
  @PostMapping("/login")
  public ResponseEntity<AuthOutputDto> login(@RequestBody @Valid AuthInputDto authInputDTO) {
    return ResponseEntity.ok(service.login(authInputDTO));
  }

  @ResponseStatus(code = HttpStatus.CREATED)
  @PostMapping("/register")
  public ResponseEntity<AuthOutputDto> saveUser(@RequestBody @Valid UserInputDto userInputDTO) {
    AuthOutputDto output = service.register(userInputDTO);
    return ResponseEntity.created(URI.create("/api/auth/me")).body(output); //201
  }

}
