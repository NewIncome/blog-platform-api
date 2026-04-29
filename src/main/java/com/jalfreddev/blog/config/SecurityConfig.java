package com.jalfreddev.blog.config;

import com.jalfreddev.blog.domain.entities.User;
import com.jalfreddev.blog.repositories.UserRepository;
import com.jalfreddev.blog.security.BlogUserDetailsService;
import com.jalfreddev.blog.security.JwtAuthenticationFilter;
import com.jalfreddev.blog.services.AuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationService authenticationService) {
    return new JwtAuthenticationFilter(authenticationService);
  }

  @Bean
  public UserDetailsService userDetailsService(UserRepository userRepository) {
    //Must modify later to have the fully build users functionality
    //for now, it's modified to autocreate one user when we start up the application, and only that one will be used
    BlogUserDetailsService blogUserDetailsService = new BlogUserDetailsService(userRepository);

    //this will create that user if it doesn't exist
    String email = "user@test.com";
    userRepository.findByEmail(email).orElseGet(() -> {
      User newUser = User.builder()
              .name("Test User")
              .email(email)
              .password(passwordEncoder().encode("mypassword"))
              .build();
      return userRepository.save(newUser);
    });

    return blogUserDetailsService;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(
                                HttpSecurity http,
                                JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
    //To specify security filters. Like for the app's EndPoints, for CSRF,and SessionManagement
    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/v1/posts/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/v1/tags/**").permitAll()
            .anyRequest().authenticated()
            //To make a quick test to see if there's a problem in the SecConfig here do only:
            // .anyRequest().permitAll()
        )
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          //this adds the jwtAuthFilter before the UserPassAuthFilter
        ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

}
