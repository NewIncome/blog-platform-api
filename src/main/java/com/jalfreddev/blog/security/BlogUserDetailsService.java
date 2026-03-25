package com.jalfreddev.blog.security;

import com.jalfreddev.blog.domain.entities.User;
import com.jalfreddev.blog.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@RequiredArgsConstructor
public class BlogUserDetailsService implements UserDetailsService {  //Is used by Spring Security to look-up users, from DB

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

    return new BlogUserDetails(user);
  }

}
