package com.zenika.academy.gazouilleur.service;

import com.zenika.academy.gazouilleur.model.comment.Comment;
import com.zenika.academy.gazouilleur.model.comment.CommentOutputDto;
import com.zenika.academy.gazouilleur.model.gazouilli.Gazouilli;
import com.zenika.academy.gazouilleur.model.gazouilli.GazouilliInputDto;
import com.zenika.academy.gazouilleur.model.gazouilli.GazouilliOutputDto;
import com.zenika.academy.gazouilleur.model.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GazouilliDtoMapperService {

  private final UserDtoMapperService userDtoMapperService;

  public GazouilliDtoMapperService(UserDtoMapperService userDtoMapperService) {

    this.userDtoMapperService = userDtoMapperService;
  }

  public Gazouilli gazouilliInputDtoToGazouilli (GazouilliInputDto gazouilliInputDto, User author) {
    Gazouilli gazouilli = new Gazouilli();
    gazouilli.setContent(gazouilliInputDto.getContent());
    gazouilli.setAuthor(author);
    return gazouilli;
  }


  //////////// gazouillis /////////////
  public GazouilliOutputDto gazouilliToGazouilliOutputDto (Gazouilli gazouilli) {
    GazouilliOutputDto gazouilliOutputDto = new GazouilliOutputDto();
    gazouilliOutputDto.setId(gazouilli.getId());
    gazouilliOutputDto.setContent(gazouilli.getContent());
    gazouilliOutputDto.setCreatedAt(gazouilli.getCreatedAt());
    gazouilliOutputDto.setAuthor(userDtoMapperService.userToAuthorOutputDto(gazouilli.getAuthor()));
    return gazouilliOutputDto;
  }

  public List<GazouilliOutputDto> gazouilliListToGazouilliOutputDtoList (List<Gazouilli> gazouillis) {
    return gazouillis.stream().map(this::gazouilliToGazouilliOutputDto).toList();
  }


  //////////// commentaires /////////////



  public CommentOutputDto commentToCommentOutputDto (Comment comment) {
    CommentOutputDto commentOutputDto = new CommentOutputDto();
    commentOutputDto.setId(comment.getId());
    commentOutputDto.setContent(comment.getContent());
    commentOutputDto.setCreatedAt(comment.getCreatedAt());
    commentOutputDto.setAuthor(userDtoMapperService.userToUserOutputDto(comment.getAuthor()));
    return commentOutputDto;
  }

  public List<CommentOutputDto> commentListToCommentOutputDtoList (List<Comment> comments) {
    return comments.stream().map(this::commentToCommentOutputDto).toList();
  }


}
