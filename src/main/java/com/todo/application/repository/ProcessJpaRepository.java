package com.todo.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.application.domain.Process;


@Repository
public interface ProcessJpaRepository extends JpaRepository<Process, Long> {
	
	List<Process> findByUserUsername(String username);

}
