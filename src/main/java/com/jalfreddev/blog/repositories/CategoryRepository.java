package com.jalfreddev.blog.repositories;

import com.jalfreddev.blog.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

  @Query("SELECT c FROM Category c LEFT JOIN FETCH c.posts")  //to use HQL(HibernateQueryLanguage) query
  List<Category> findAllWithPostCount();
  /* That query helps to remove a n+1 problem to only do 1 query,
     Without it, doing a findAll() would return a list of categories,
     which in turn (hibernate)would query and get a list of posts-per-category,
     which would lead to a great number of queries being sent to the DB
   */

  boolean existsByNameIgnoreCase(String name);

}
