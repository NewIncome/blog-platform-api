package com.jalfreddev.blog.controllers;

import com.jalfreddev.blog.domain.dtos.CategoryDto;
import com.jalfreddev.blog.domain.entities.Category;
import com.jalfreddev.blog.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping
  public ResponseEntity<List<CategoryDto>> listCategories() {
    List<Category> categories = categoryService.listCategories();
    return categories;
  }

}
