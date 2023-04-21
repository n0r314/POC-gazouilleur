package com.zenika.academy.gazouilleur.service;

import com.zenika.academy.gazouilleur.model.exception.ForbiddenException;
import com.zenika.academy.gazouilleur.model.exception.NotFoundException;
import com.zenika.academy.gazouilleur.model.exception.UnauthorizedException;
import com.zenika.academy.gazouilleur.model.exception.UsernameAlreadyExistsException;
import com.zenika.academy.gazouilleur.model.gazouilli.Gazouilli;
import com.zenika.academy.gazouilleur.model.user.*;
import com.zenika.academy.gazouilleur.storage.UserJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

  private final UserJpaRepository userRepository;
  private final UserDtoMapperService mapperService;

  protected static final Logger LOGGER = LoggerFactory.getLogger("user-service");

  public UserService(UserJpaRepository userRepository, UserDtoMapperService mapperService) {
    this.userRepository = userRepository;
    this.mapperService = mapperService;
  }

  public List<UserOutputDto> listUsers() {
    LOGGER.info("Je retourne la liste de tous les utilisateurs");
    return mapperService.userListToUserOutputDtoList(userRepository.findAll());
  }

  public UserOutputDto getUser(UUID id) {

    User user = userRepository.findById(id)
      .orElseThrow(() -> new NotFoundException(User.class, id));

    // on vérifie qu'on est authentifié
    if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass().equals(UserAuthenticationDetails.class)) {
      // on récupère l'utilisateur authentifié
      UserAuthenticationDetails userDetails = (UserAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      User authenticatedUser = userDetails.getUser();

      // si c'est bien lui l'utilisateur ou si on est admin, on continue, sinon on envoie un "forbidden"
      if (authenticatedUser.getId().equals(user.getId()) || userDetails.isAdmin()) {
        // on continue
        LOGGER.info("Je retourne l'utilisateur " + id);
        return mapperService.userToUserOutputDto(user);
      }
      // si ce n'est pas moi que je cherche à voir, on renvoie un 403
      else {
        throw new ForbiddenException();
      }
    }
    // s'il n'est pas authentifié on renvoie une 401
    else {
      throw new UnauthorizedException();
    }

  }



  public AuthorOutputDto getAuthor(UUID id) {

    User user = userRepository.findById(id)
      .orElseThrow(() -> new NotFoundException(User.class, id));

    LOGGER.info("Je retourne l'utilisateur " + id + " sous forme d'auteur");
    return mapperService.userToAuthorOutputDto(user);
  }

  @Transactional
  public UserOutputDto createUser(UserInputDto userInputDto) {
    // on renvoie une 409 si l'utilisateur existe déjà
    if (userRepository.existsByUsernameIgnoreCase(userInputDto.getUsername())) {
      throw new UsernameAlreadyExistsException(userInputDto.getUsername());
    }
    User userToSave = mapperService.userInputDtoToUser(userInputDto);
    User savedUser = userRepository.save(userToSave);
    LOGGER.info("j'enregistre un utilisateur " + savedUser.getId());
    return mapperService.userToUserOutputDto(savedUser);
  }

  @Transactional
  public void deleteUser(UUID id) {
    // on vérifie que l'utilisateur est présent
    User user = userRepository.findById(id)
      .orElseThrow(() -> new NotFoundException(Gazouilli.class, id));

    // on vérifie qu'on est authentifié
    if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass().equals(UserAuthenticationDetails.class)) {
      // on récupère l'utilisateur authentifié
      UserAuthenticationDetails userDetails = (UserAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      User authenticatedUser = userDetails.getUser();

      // si c'est bien lui l'utilisateur ou si on est admin, on continue, sinon on envoie un "forbidden"
      if (authenticatedUser.getId().equals(user.getId()) || userDetails.isAdmin()) {
        // on supprime le gazouilli
        LOGGER.info("Je supprime le gazouilli " + id);
        userRepository.delete(user);
      }
      // si ce n'est pas moi que je cherche à supprimer, on renvoie un 403
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
  public UserOutputDto updateUser(UUID id, UserUpdateInputDto userUpdateInputDto) {
    // on vérifie que l'utilisateur est présent
    User user = userRepository.findById(id)
      .orElseThrow(() -> new NotFoundException(Gazouilli.class, id));

    // on vérifie qu'on est authentifié
    if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass().equals(UserAuthenticationDetails.class)) {
      // on récupère l'utilisateur authentifié
      UserAuthenticationDetails userDetails = (UserAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      User authenticatedUser = userDetails.getUser();

      // si c'est bien lui l'utilisateur ou si on est admin, on continue, sinon on envoie un "forbidden"
      if (authenticatedUser.getId().equals(user.getId()) || userDetails.isAdmin()) {
        // on modifie l'utilisateur
        // on tranforme l'input DTO en User
        User userWithUpdatedValues = mapperService.userUpdateInputDtoToUser(user, userUpdateInputDto);
        // on lui donne le bon id = celui dans le path
        userWithUpdatedValues.setId(id);
        // on enregistre cet User dans la base, il va automatiquement remplacer l'User qui avait le même id
        User updatedUser = userRepository.save(userWithUpdatedValues);
        // on renvoie le gazouilli modifié
        LOGGER.info("Je modifie l'utilisateur " + id);
        return mapperService.userToUserOutputDto(updatedUser);
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

