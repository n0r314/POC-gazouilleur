package com.zenika.academy.gazouilleur.controller;

import com.zenika.academy.gazouilleur.AbstractIntegrationTests;
import com.zenika.academy.gazouilleur.model.user.User;
import com.zenika.academy.gazouilleur.service.AuthService;
import com.zenika.academy.gazouilleur.storage.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTests extends AbstractIntegrationTests {

  @Autowired
  private UserJpaRepository userRepository;

  @Autowired
  private AuthService authService;

  @Autowired
  private MockMvc mockMvc;

  private User userBob() {
    final var user = new User();
    user.setUsername("bob");
    //secret
    user.setPassword("{bcrypt}$2y$10$8W1IiFpKtSjE3e6XPLKiwu4D1gfz6sHI3Am9gYKusQu0l87qe4dK2");
    user.setNickname("Bob L'Eponge");
    user.setAvatar("http://mockavatar.com");
    user.setDescription("C'est moi.");
    return user;
  }

  @BeforeEach
  public void setup() {
    userRepository.deleteAll();
  }

  @Test
  public void repository_initialized_correctly() {
    assertThat(userRepository).isNotNull();
  }


  @Test
  public void given_user_when_GetAuthorById_thenStatus200() throws Exception {

    User bob = userBob();
    User bobInBase = userRepository.save(bob);
    String bobInBaseId = bobInBase.getId().toString();

    mockMvc.perform(get("/api/users/author/" + bobInBaseId)
        .accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.username").value("bob"));
  }

  @Test
  public void given_user_when_GetUserById_with_user_authenticated_thenStatus200() throws Exception {

    User bob = userBob();
    User bobInBase = userRepository.save(bob);
    String bobInBaseId = bobInBase.getId().toString();
    String token = authService.getToken(bob.getUsername(), "secret");

    mockMvc.perform(get("/api/users/" + bobInBaseId)
        .header(HttpHeaders.AUTHORIZATION,
          "Basic " + token)
        .accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.username").value("bob"));
  }


}
