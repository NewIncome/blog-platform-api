package com.jalfreddev.blog.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {

  UserDetails authenticate(String email, String password);

  String generateToken(UserDetails userDetails);  //will turn UserDetails → Jwt

  UserDetails validateToken(String token);  //will turn Jwt → UserDetails

}
