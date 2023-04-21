package com.zenika.academy.gazouilleur.storage;

import com.zenika.academy.gazouilleur.AbstractIntegrationTests;
import com.zenika.academy.gazouilleur.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserJpaRepositoryTests extends AbstractIntegrationTests {

  @Autowired
  private UserJpaRepository userRepository;

  @BeforeEach
  public void setup() {
    userRepository.deleteAll();
  }

  private User userBob() {
    User bob = new User();
    bob.setUsername("bob");
    bob.setPassword("{bcrypt}$2y$10$8W1IiFpKtSjE3e6XPLKiwu4D1gfz6sHI3Am9gYKusQu0l87qe4dK2");
    bob.setNickname("Bob L'Eponge");
    bob.setAvatar("https://mockavatar.com");
    bob.setDescription("C'est moi.");
    return bob;
  }

  @Test
  public void when_save_new_user_user_is_saved() {
    // given
    User bob = userBob();
    int nbUsers = userRepository.findAll().size();

    // when
    User saved = userRepository.save(bob);

    // then
    assertThat(saved.getNickname())
      .isEqualTo(bob.getNickname());
    assertThat(userRepository.findAll().size())
      .isEqualTo(nbUsers + 1);
  }

  @Test
  public void when_search_with_username_bob_userBob_is_found() {
    // given
    User bob = userBob();
    userRepository.save(bob);

    // when
    User found = userRepository.findByUsernameIgnoreCase("bob").orElseThrow();

    // then
    assertThat(found.getNickname())
      .isEqualTo("Bob L'Eponge");
  }

  @Test
  public void when_delete_userBob_userBob_deleted()  {
    // given
    User bob = userBob();
    User bobInBase = userRepository.save(bob);
    int nbUsers = userRepository.findAll().size();

    // when
    userRepository.delete(bobInBase);

    // then
    assertThat(userRepository.findAll().size())
      .isEqualTo(nbUsers - 1);
  }

}
