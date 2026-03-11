package com.jalfreddev.blog.repositories;

import com.jalfreddev.blog.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {  //for all CRUD func, and pagination
}
