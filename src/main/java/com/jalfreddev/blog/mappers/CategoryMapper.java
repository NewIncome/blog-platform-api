package com.jalfreddev.blog.mappers;

import com.jalfreddev.blog.domain.PostStatus;
import com.jalfreddev.blog.domain.dtos.CategoryDto;
import com.jalfreddev.blog.domain.entities.Category;
import com.jalfreddev.blog.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

//componentModel: for the type of application,
//unmappedTargetPolicy: to select an action when it can't map something, instead of throwing an error
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

  //annotation for additional fields
  @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
  CategoryDto toDto(Category category);

  @Named("calculatePostCount")
  default long calculatePostCount(List<Post> posts) {
    if(posts == null) return 0;

    return posts.stream()
              .filter(post -> PostStatus.PUBLISHED.equals(post.getStatus()))
              .count();
  }

}
