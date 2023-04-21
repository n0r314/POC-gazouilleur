package com.zenika.academy.gazouilleur.controller;

import com.zenika.academy.gazouilleur.model.user.*;
import com.zenika.academy.gazouilleur.service.UserDtoMapperService;
import com.zenika.academy.gazouilleur.service.UserService;
import com.zenika.academy.gazouilleur.storage.UserJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @ResponseStatus(code = HttpStatus.OK)
  @GetMapping
  public ResponseEntity<List<UserOutputDto>> getUsers() {
    return ResponseEntity.ok(userService.listUsers());
  }

  @ResponseStatus(code = HttpStatus.OK)
  @GetMapping("/{id}")
  public ResponseEntity<UserOutputDto> getUserById(@PathVariable("id") UUID id) {
    return ResponseEntity.ok(userService.getUser(id)); //200
  }

  @ResponseStatus(code = HttpStatus.OK)
  @GetMapping("/author/{id}")
  public ResponseEntity<AuthorOutputDto> getAuthorById(@PathVariable("id") UUID id) {
    return ResponseEntity.ok(userService.getAuthor(id)); //200
  }

  @ResponseStatus(code = HttpStatus.CREATED)
  @PostMapping
  public ResponseEntity<UserOutputDto> saveUser(@RequestBody @Valid UserInputDto userInputDto) {
    UserOutputDto output = userService.createUser(userInputDto);
    return ResponseEntity.created(URI.create("/api/users/" + output.getId())).body(output); //201
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUserById(@PathVariable("id") UUID id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserOutputDto> updateUser(@PathVariable("id") UUID id, @RequestBody @Valid UserUpdateInputDto userUpdateInputDto) {
    UserOutputDto output = userService.updateUser(id, userUpdateInputDto);
    return ResponseEntity.ok(output);
  }




}
