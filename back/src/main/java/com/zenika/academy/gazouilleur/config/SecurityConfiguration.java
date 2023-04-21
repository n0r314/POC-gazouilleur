package com.zenika.academy.gazouilleur.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
  public SecurityConfiguration() {
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      // pour supprimer le login fait par défaut
      .formLogin().disable()
      // pour supprimer le csrf sur les post/put
      .csrf().disable()
      // pour virer les cookies de session
      .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      // on fait du basic, sans l'entrypoint par défaut qui ouvre un popup
      .httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))


      .authorizeHttpRequests()
      // pour swagger:  taper sur /swagger-ui/index.html
      .antMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
      .antMatchers(HttpMethod.GET, "/v3/**").permitAll()
      // pour la CI
      .antMatchers(HttpMethod.GET, "/health").permitAll()
      // gazouillis
      .antMatchers(HttpMethod.POST, "/api/gazouillis").hasRole("USER")
      .antMatchers(HttpMethod.PUT, "/api/gazouillis/*").hasRole("USER")
      .antMatchers(HttpMethod.DELETE, "/api/gazouillis/*").hasRole("USER")
      .antMatchers(HttpMethod.GET, "/api/gazouillis").permitAll()
      .antMatchers(HttpMethod.GET, "/api/gazouillis/*").permitAll()
      .antMatchers(HttpMethod.GET, "/api/gazouillis/*/comments").permitAll()
      .antMatchers(HttpMethod.POST, "/api/gazouillis/*/comments").hasRole("USER")
      .antMatchers("/api/gazouillis/*/comments/*").hasRole("USER")
      // auth
      .antMatchers(HttpMethod.GET, "/api/auth/me").permitAll()
      .antMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
      .antMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
      // users
      .antMatchers("/api/users").hasRole("ADMIN")
      .antMatchers("/api/users/*").hasRole("USER")
      .antMatchers("/api/users/author/*").permitAll()

      // toutes les requêtes non précisées doivent être authentifiées en admin
      .anyRequest().hasRole("ADMIN")
    ;

    return http.build();
  }

}
