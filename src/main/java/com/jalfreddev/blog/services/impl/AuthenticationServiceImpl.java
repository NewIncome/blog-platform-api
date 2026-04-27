package com.jalfreddev.blog.services.impl;

import com.jalfreddev.blog.services.AuthenticationService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;

  //the one from springBeans, not from lombok
  @Value("${jwt.secret}")  //We will load it from our configuration
  private String secretKey;

  private Long jwtExpiryMs = 86400000L;  //hardcoded to 24hrs. Could be configurable

  @Override
  public UserDetails authenticate(String email, String password) {
    //We could delegate to the AuthenticationManager, → which delegates to the AuthenticationProvider
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(email, password)
    );
    // As for the unsuccessful scenario, The AuthManager throws the error,
    // and is handled by our ErrorController.
    return userDetailsService.loadUserByUsername(email);
  }

  @Override
  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date( System.currentTimeMillis() ))
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpiryMs))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
  }

  private Key getSigningKey() {
    byte[] keyBytes = secretKey.getBytes();
    return Keys.hmacShaKeyFor(keyBytes);
  }

}
