package com.todo.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.application.domain.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, String> {
	User findByUsername(String username);
	User findByEmail(String email);
	User findByEmailIgnoreCase(String emailId);
}
