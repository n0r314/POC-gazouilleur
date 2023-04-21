package com.zenika.academy.gazouilleur.service;

import com.zenika.academy.gazouilleur.model.user.User;
import com.zenika.academy.gazouilleur.model.user.UserAuthenticationDetails;
import com.zenika.academy.gazouilleur.storage.UserJpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService implements UserDetailsService {

  private final UserJpaRepository userJpaRepository;

  public UserAuthenticationService(UserJpaRepository userJpaRepository) {
    this.userJpaRepository = userJpaRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    User user = userJpaRepository.findByUsernameIgnoreCase(username).orElseThrow(() -> new UsernameNotFoundException(username));
    return new UserAuthenticationDetails(user);
  }
}
