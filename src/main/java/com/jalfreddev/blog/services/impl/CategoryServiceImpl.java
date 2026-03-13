package com.jalfreddev.blog.services.impl;

import com.jalfreddev.blog.domain.entities.Category;
import com.jalfreddev.blog.repositories.CategoryRepository;
import com.jalfreddev.blog.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  @Override
  public List<Category> listCategories() {
    return categoryRepository.findAllWithPostCount();
  }

}
