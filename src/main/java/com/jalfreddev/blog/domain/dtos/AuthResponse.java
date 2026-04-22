/**
 *  Needed for the Response, of AuthController::login() {.}
 */
package com.jalfreddev.blog.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

  private String token;  //JWT value.  To have it available easily. It's inside the token itself
  private Long expiresIn;

}
