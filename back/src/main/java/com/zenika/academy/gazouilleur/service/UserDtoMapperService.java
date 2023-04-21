package com.zenika.academy.gazouilleur.service;

import com.zenika.academy.gazouilleur.model.user.*;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDtoMapperService {


  public UserDtoMapperService() {
  }

  public User userInputDtoToUser(UserInputDto userInputDto) {
    User user = new User();
    user.setUsername(userInputDto.getUsername());
    // on encode le mot de passe avec l'encodeur par défaut = bcrypt
    user.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(userInputDto.getPassword()));
    user.setNickname(userInputDto.getNickname());
    user.setAvatar(userInputDto.getAvatar() != null? user.getAvatar(): "https://miro.medium.com/max/720/1*W35QUSvGpcLuxPo3SRTH4w.png");
    user.setDescription(userInputDto.getDescription()!= null? user.getDescription(): "");
    return user;
  }


  public User userUpdateInputDtoToUser(User user, UserUpdateInputDto userUpdateInputDto) {
    User modifiedUser = new User();
    modifiedUser.setId(user.getId());
    modifiedUser.setUsername(user.getUsername());
    modifiedUser.setPassword(user.getPassword());
    modifiedUser.setNickname(userUpdateInputDto.getNickname());
    modifiedUser.setAvatar(userUpdateInputDto.getAvatar() != null? userUpdateInputDto.getAvatar(): "https://miro.medium.com/max/720/1*W35QUSvGpcLuxPo3SRTH4w.png");
    modifiedUser.setDescription(userUpdateInputDto.getDescription()!= null? userUpdateInputDto.getDescription(): "");
    return modifiedUser;
  }

  public UserOutputDto userToUserOutputDto(User user) {
    UserOutputDto userOutputDto = new UserOutputDto();
    userOutputDto.setId(user.getId());
    userOutputDto.setUsername(user.getUsername());
    userOutputDto.setPassword(user.getPassword());
    userOutputDto.setNickname(user.getNickname());
    userOutputDto.setAvatar(user.getAvatar());
    userOutputDto.setDescription(user.getDescription());
    return userOutputDto;
  }

  public List<UserOutputDto> userListToUserOutputDtoList (List<User> users) {
    return users.stream().map(this::userToUserOutputDto).toList();
  }


  public AuthorOutputDto userToAuthorOutputDto(User user) {
    AuthorOutputDto authorOutputDto = new AuthorOutputDto();
    authorOutputDto.setId(user.getId());
    authorOutputDto.setUsername(user.getUsername());
    authorOutputDto.setNickname(user.getNickname());
    authorOutputDto.setAvatar(user.getAvatar());
    authorOutputDto.setDescription(user.getDescription());
    return authorOutputDto;
  }

}
