package com.zenika.academy.gazouilleur.controller;


import com.zenika.academy.gazouilleur.model.comment.CommentInputDto;
import com.zenika.academy.gazouilleur.model.comment.CommentOutputDto;
import com.zenika.academy.gazouilleur.model.gazouilli.GazouilliInputDto;
import com.zenika.academy.gazouilleur.model.gazouilli.GazouilliOutputDto;
import com.zenika.academy.gazouilleur.service.GazouilliService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/gazouillis")
public class GazouilliController {

  private final GazouilliService service;


  public GazouilliController(GazouilliService service) {
    this.service = service;
  }

  ////////// gazouillis /////////
  @ResponseStatus(code = HttpStatus.OK)
  @GetMapping
  public ResponseEntity<List<GazouilliOutputDto>> getGazouillis(@RequestParam("hashtag") Optional<String> hashtag, @RequestParam("authorname") Optional<String> authorname) {
    return ResponseEntity.ok(service.listGazouillis(hashtag, authorname));
  }

  @ResponseStatus(code = HttpStatus.OK)
  @GetMapping("/{id}")
  public ResponseEntity<GazouilliOutputDto> getGazouilliById(@PathVariable("id") UUID id) {
    return ResponseEntity.ok(service.getGazouilli(id));
  }

  @ResponseStatus(code = HttpStatus.CREATED)
  @PostMapping
  public ResponseEntity<GazouilliOutputDto> saveGazouilli(@RequestBody @Valid GazouilliInputDto input) {
    GazouilliOutputDto output = service.createGazouilli(input);
    return ResponseEntity.created(URI.create("/api/gazouillis/" + output.getId())).body(output); //201
  }

  @ResponseStatus(code = HttpStatus.OK)
  @PutMapping("/{id}")
  public ResponseEntity<GazouilliOutputDto> updateArticle(@PathVariable("id") UUID id, @RequestBody @Valid GazouilliInputDto input) {
    return ResponseEntity.ok(service.updateGazouilli(id, input));
  }

  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteGazouilliById(@PathVariable("id") UUID id) {
    service.deleteGazouilli(id);
    return ResponseEntity.noContent().build();
  }

  ////////// commentaires /////////
  @ResponseStatus(code = HttpStatus.OK)
  @GetMapping("/{gazouilliId}/comments")
  public ResponseEntity<List<CommentOutputDto>> getComments(@PathVariable("gazouilliId") UUID gazouilliId) {
    return ResponseEntity.ok(service.listComments(gazouilliId));
  }

  @ResponseStatus(code = HttpStatus.CREATED)
  @PostMapping("/{gazouilliId}/comments")
  public ResponseEntity<CommentOutputDto> saveComment(@PathVariable("gazouilliId") UUID gazouilliId, @RequestBody @Valid CommentInputDto input) {
    CommentOutputDto output = service.createComment(gazouilliId, input);
    return ResponseEntity.created(URI.create("/api/gazouillis/" + gazouilliId + "/comments/" + output.getId())).body(output); //201
  }

  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  @DeleteMapping("/{gazouilliId}/comments/{commentId}")
  public ResponseEntity<Void> deleteCommentById(@PathVariable("gazouilliId") UUID gazouilliId, @PathVariable("commentId") UUID commentId) {
    service.deleteComment(gazouilliId, commentId);
    return ResponseEntity.noContent().build();
  }


}
