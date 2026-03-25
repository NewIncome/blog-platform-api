package com.jalfreddev.blog.services.impl;

import com.jalfreddev.blog.domain.entities.Category;
import com.jalfreddev.blog.repositories.CategoryRepository;
import com.jalfreddev.blog.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  @Override
  public List<Category> listCategories() {
    return categoryRepository.findAllWithPostCount();
  }

  @Override
  @Transactional  //because we are making multiple DB calls, to be made in the same transaction
  public Category createCategory(Category category) {
    String categoryName = category.getName();
    if(categoryRepository.existsByNameIgnoreCase(categoryName)) {
      throw new IllegalArgumentException("Category already exists with name: " + categoryName);
    }

    return categoryRepository.save(category);
  }

  @Override
  public void deleteCategory(UUID id) {
    //due to our design decision we can't delete categories that have posts associated with them
    Optional<Category> category = categoryRepository.findById(id);
    if(category.isPresent())
      if(category.get().getPosts().size() > 0) {
        throw new IllegalArgumentException("Category has posts associated with it");
      }

    categoryRepository.deleteById(id);
  }

}
