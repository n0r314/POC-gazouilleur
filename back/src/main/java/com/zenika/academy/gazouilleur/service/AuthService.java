package com.zenika.academy.gazouilleur.service;

import com.zenika.academy.gazouilleur.model.auth.AuthInputDto;
import com.zenika.academy.gazouilleur.model.auth.AuthOutputDto;
import com.zenika.academy.gazouilleur.model.exception.UnauthorizedException;
import com.zenika.academy.gazouilleur.model.exception.UsernameAlreadyExistsException;
import com.zenika.academy.gazouilleur.model.user.User;
import com.zenika.academy.gazouilleur.model.user.UserAuthenticationDetails;
import com.zenika.academy.gazouilleur.model.user.UserInputDto;
import com.zenika.academy.gazouilleur.storage.UserJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class AuthService {

  private final UserJpaRepository userRepository;
  private final UserDtoMapperService mapperService;
  private static final Logger LOGGER = LoggerFactory.getLogger("auth-service");

  public AuthService(UserJpaRepository userRepository, UserDtoMapperService mapperService) {
    this.userRepository = userRepository;
    this.mapperService = mapperService;
  }


  public AuthOutputDto getMe(String authToken) {

    // on cherche qui est identifié on le transforme en authoutputdto
    if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass().equals(UserAuthenticationDetails.class)) {
      UserAuthenticationDetails userDetails = (UserAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      User authUser = userDetails.getUser();
      AuthOutputDto output = new AuthOutputDto();
      output.setUser(mapperService.userToUserOutputDto(authUser));
      output.setToken(authToken.replace("Basic ",""));
      return output;
    } else {
      throw new UnauthorizedException();
    }
  }

  public AuthOutputDto login(AuthInputDto authInputDTO) {
    // si l'utilisateur est présent en base...
    if (userRepository.existsByUsernameIgnoreCase(authInputDTO.getUsername())) {
      User databaseUser = userRepository.findByUsernameIgnoreCase(authInputDTO.getUsername()).get();
      // et que le mot de passe est bon...
      if (PasswordEncoderFactories.createDelegatingPasswordEncoder().matches(authInputDTO.getPassword(), databaseUser.getPassword())) {
        // on crée un AuthOuput avec un userOutputDto et un token
        AuthOutputDto output = new AuthOutputDto();
        output.setUser(mapperService.userToUserOutputDto(databaseUser));
        output.setToken(getToken(authInputDTO.getUsername(), authInputDTO.getPassword()));
        return output;
      } else {
        // si le mot de passe est faux on renvoie une 401
        throw new UnauthorizedException();
      }
    } else {
      // si l'utilisateur est faux on renvoie une 401
      throw new UnauthorizedException();
    }
  }

  public AuthOutputDto register(UserInputDto input) {
    User userToSave = mapperService.userInputDtoToUser(input);

    // on renvoie une 409 si l'utilisateur existe déjà
    if (userRepository.existsByUsernameIgnoreCase(userToSave.getUsername())) {
      throw new UsernameAlreadyExistsException(userToSave.getUsername());
    }

    User savedUser = userRepository.save(userToSave);
    AuthOutputDto output = new AuthOutputDto();
    output.setUser(mapperService.userToUserOutputDto(savedUser));
    output.setToken(getToken(input.getUsername(), input.getPassword()));
    LOGGER.info("j'enregistre un utilisateur " + savedUser.getId());
    return output;
  }

  public String getToken(String username, String password) {
    String clearToken = username + ":" + password;
    return Base64.getEncoder().encodeToString(clearToken.getBytes());
  }


}
