package com.jalfreddev.blog.security;

import com.jalfreddev.blog.services.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {  //'cause we will run this oncePerRequest

  private final AuthenticationService authenticationService;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    try {
      String token = extractToken(request);
      if (token != null) {
        UserDetails userDetails = authenticationService.validateToken(token);
        UsernamePasswordAuthenticationToken authenticationObj = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        );
        //to set this in our securityContext. to make our authObject available for the rest of the request.
        // Essentially setting the authenticated User
        SecurityContextHolder.getContext().setAuthentication(authenticationObj);

        //set the UserId to be accessible throughout the request
        // to not only lookup the User by email. For possible later purposes
        if (userDetails instanceof BlogUserDetails) {
          request.setAttribute("userId", ((BlogUserDetails) userDetails).getId());
          //here we use casting to cast UserDetails into BlogUserDetails
        }
      }
      //try catch block to not handle all the exceptions it might throw for unauthenticated users/tries
    } catch (Exception ex) {
      // Do not throw exceptions, just don't authenticate the user
      log.warn("Received invalid auth token");
    }

    filterChain.doFilter(request, response);
  }

  private String extractToken(HttpServletRequest request) {
    //A jwt will be provided in the request prefix with the word bearer
    String bearerToken = request.getHeader("Authorization");
    if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

}
