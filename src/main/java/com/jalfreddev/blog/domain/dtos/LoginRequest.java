/**
 *  Needed for the RequestBody param, for AuthController::login(.)
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
public class LoginRequest {

  private String email;
  private String password;

}
