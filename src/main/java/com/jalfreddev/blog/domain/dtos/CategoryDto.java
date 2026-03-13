package com.jalfreddev.blog.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data  //here we can use it because DTOs don't interact with Spring Data JPA
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

  private UUID id;
  private String name;
  private long postCount;

}
