package com.jalfreddev.blog.services;

import com.jalfreddev.blog.domain.entities.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

  List<Category> listCategories();

  Category createCategory(Category category);

  void deleteCategory(UUID id);

}
