package com.todo.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.application.domain.ToDo;

@Repository
public interface ToDoJpaRepository extends JpaRepository<ToDo, Long> {
	
//	List<ToDo> findByUsername(String username);
	
	List<ToDo> findByUserUsername(String username);
	
	List<ToDo> findByProcessProcessId(Long id);

	
	
}
