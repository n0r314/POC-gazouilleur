package com.zenika.academy.gazouilleur.service;

import com.zenika.academy.gazouilleur.model.comment.Comment;
import com.zenika.academy.gazouilleur.model.comment.CommentInputDto;
import com.zenika.academy.gazouilleur.model.comment.CommentOutputDto;
import com.zenika.academy.gazouilleur.model.exception.ForbiddenException;
import com.zenika.academy.gazouilleur.model.exception.NotFoundException;
import com.zenika.academy.gazouilleur.model.exception.UnauthorizedException;
import com.zenika.academy.gazouilleur.model.gazouilli.Gazouilli;
import com.zenika.academy.gazouilleur.model.gazouilli.GazouilliInputDto;
import com.zenika.academy.gazouilleur.model.gazouilli.GazouilliOutputDto;
import com.zenika.academy.gazouilleur.model.user.User;
import com.zenika.academy.gazouilleur.model.user.UserAuthenticationDetails;
import com.zenika.academy.gazouilleur.storage.CommentJpaRepository;
import com.zenika.academy.gazouilleur.storage.GazouilliJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GazouilliService {

  private final GazouilliJpaRepository gazouilliRepository;
  private final CommentJpaRepository commentRepository;
  private final GazouilliDtoMapperService mapperService;

  protected static final Logger LOGGER = LoggerFactory.getLogger("gazouilli-service");


  ////////// gazouillis ////////////
  public GazouilliService(GazouilliJpaRepository gazouilliRepository, CommentJpaRepository commentRepository, GazouilliDtoMapperService mapperService) {
    this.gazouilliRepository = gazouilliRepository;
    this.commentRepository = commentRepository;
    this.mapperService = mapperService;
  }

  public List<GazouilliOutputDto> listGazouillis(Optional<String> hashtag, Optional<String> authorname) {

    List<Gazouilli> gazouillis;
    if (authorname.isPresent()) {
      LOGGER.info("je retourne tous les gazouillis avec " + authorname.get() + " comme auteur");
      gazouillis = gazouilliRepository.findAllByAuthor_UsernameOrderByCreatedAtDesc(authorname.get());
    } else if (hashtag.isPresent()) {
      LOGGER.info("je retourne tous les gazouillis avec le hastag " + hashtag.get());
      gazouillis = gazouilliRepository.findAllByContentContainingIgnoreCaseOrderByCreatedAtDesc(hashtag.get());
    } else {
      LOGGER.info("je retourne tous les gazouillis");
      gazouillis = gazouilliRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }
    return mapperService.gazouilliListToGazouilliOutputDtoList(gazouillis);
  }

  public GazouilliOutputDto getGazouilli(UUID id) {
    Gazouilli gazouilli = gazouilliRepository.findById(id)
      .orElseThrow(() -> new NotFoundException(Gazouilli.class, id));
    LOGGER.info("Je retourne la liste du gazouilli " + id);
    return mapperService.gazouilliToGazouilliOutputDto(gazouilli);
  }

  @Transactional
  public GazouilliOutputDto createGazouilli(GazouilliInputDto gazouilliInputDto) {

    User author;
    // on cherche qui est identifié, on le met en auteur et sinon unauthorized
    if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass().equals(UserAuthenticationDetails.class)) {
      UserAuthenticationDetails userDetails = (UserAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      author = userDetails.getUser();
    } else {
      throw new UnauthorizedException();
    }

    Gazouilli gazouilli = mapperService.gazouilliInputDtoToGazouilli(gazouilliInputDto, author);
    gazouilli.setCreatedAt(Instant.now());
    Gazouilli savedGazouilli = gazouilliRepository.save(gazouilli);
    LOGGER.info("Je créer un nouveau gazouilli " + savedGazouilli.getId());
    return mapperService.gazouilliToGazouilliOutputDto(savedGazouilli);
  }

  @Transactional
  public void deleteGazouilli(UUID id) {
    // on vérifie que le gazouilli est présent
    Gazouilli gazouilli = gazouilliRepository.findById(id)
      .orElseThrow(() -> new NotFoundException(Gazouilli.class, id));

    // on vérifie qu'on est authentifié
    if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass().equals(UserAuthenticationDetails.class)) {
      // on récupère l'utilisateur authentifié
      UserAuthenticationDetails userDetails = (UserAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      User authenticatedUser = userDetails.getUser();

      // si c'est bien l'auteur du gazouilli ou si on est un admin, on continue, sinon on envoie un "forbidden"
      if (authenticatedUser.getId().equals(gazouilli.getAuthor().getId()) || userDetails.isAdmin()) {
        // on supprime le gazouilli
        LOGGER.info("Je supprime le gazouilli " + id);
        gazouilliRepository.delete(gazouilli);
      }
      // si ce n'est pas moi l'auteur, on renvoie un 403
      else {
        throw new ForbiddenException();
      }
    }
    // s'il n'est pas authentifié on renvoie une 401
    else {
      throw new UnauthorizedException();
    }

  }

  @Transactional
  public GazouilliOutputDto updateGazouilli(UUID id, GazouilliInputDto gazouilliInputDto) {
    // on vérifie que le gazouilli est présent
    Gazouilli gazouilli = gazouilliRepository.findById(id)
      .orElseThrow(() -> new NotFoundException(Gazouilli.class, id));

    // on vérifie qu'on est authentifié
    if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass().equals(UserAuthenticationDetails.class)) {
      // on récupère l'utilisateur authentifié
      UserAuthenticationDetails userDetails = (UserAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      User authenticatedUser = userDetails.getUser();

      // si c'est bien l'auteur du gazouilli ou si c'est un admin, on continue, sinon on envoie un "forbidden"
      if (authenticatedUser.getId().equals(gazouilli.getAuthor().getId()) || userDetails.isAdmin()) {
        // on modifie le contenu
        gazouilli.setContent(gazouilliInputDto.getContent());
        // on enregistre dans la base le gazouilli modifié
        Gazouilli savedGazouilli = gazouilliRepository.save(gazouilli);
        // on renvoie le gazouilli modifié
        LOGGER.info("Je modifie le gazouilli" + id);
        return mapperService.gazouilliToGazouilliOutputDto(savedGazouilli);
      }
      // si ce n'est pas moi l'auteur, on renvoie un 403
      else {
        throw new ForbiddenException();
      }
    }
    // s'il n'est pas authentifié on renvoie une 401
    else {
      throw new UnauthorizedException();
    }
  }


  ////////// commentaires ////////////
  public List<CommentOutputDto> listComments(UUID gazouilliId) {

    LOGGER.info("je retourne tous les commentaires du gazouilli " + gazouilliId);
    return mapperService.commentListToCommentOutputDtoList(commentRepository.findAllByGazouilli_IdOrderByCreatedAtDesc(gazouilliId));
  }


  @Transactional
  public CommentOutputDto createComment(UUID gazouilliId, CommentInputDto commentInputDto) {

    Comment comment = new Comment();

    // content
    comment.setContent(commentInputDto.getContent());

    // auteur
    User author;
    // on cherche qui est identifié, on le met en auteur et sinon unauthorized
    if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass().equals(UserAuthenticationDetails.class)) {
      UserAuthenticationDetails userDetails = (UserAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      author = userDetails.getUser();
    } else {
      throw new UnauthorizedException();
    }
    comment.setAuthor(author);

    // gazouilli
    Gazouilli gazouilli = gazouilliRepository.findById(gazouilliId).orElseThrow(() -> new NotFoundException(Gazouilli.class, gazouilliId));
    comment.setGazouilli(gazouilli);

    // createdAt
    comment.setCreatedAt(Instant.now());

    Comment savedComment = commentRepository.save(comment);
    LOGGER.info("Je créer un nouveau gazouilli " + savedComment.getId());
    return mapperService.commentToCommentOutputDto(savedComment);
  }


  @Transactional
  public void deleteComment(UUID gazouilliId, UUID commentId) {
    // on vérifie que le gazouilli est présent
    gazouilliRepository.findById(gazouilliId)
      .orElseThrow(() -> new NotFoundException(Gazouilli.class, gazouilliId));

    // on vérifie que le commentaire est présent
    Comment comment = commentRepository.findById(commentId)
      .orElseThrow(() -> new NotFoundException(Comment.class, commentId));

    // on vérifie qu'on est authentifié
    if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass().equals(UserAuthenticationDetails.class)) {
      // on récupère l'utilisateur authentifié
      UserAuthenticationDetails userDetails = (UserAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      User authenticatedUser = userDetails.getUser();

      // si c'est bien l'auteur ou si on est un admin, on continue, sinon on envoie un "forbidden"
      if (authenticatedUser.getId().equals(comment.getAuthor().getId()) || userDetails.isAdmin()) {
        // on supprime le commentaire
        LOGGER.info("Je supprime le commentaire " + commentId);
        commentRepository.delete(comment);
      }
      // si ce n'est pas moi l'auteur, on renvoie un 403
      else {
        throw new ForbiddenException();
      }
    }
    // s'il n'est pas authentifié on renvoie une 401
    else {
      throw new UnauthorizedException();
    }

  }

}

