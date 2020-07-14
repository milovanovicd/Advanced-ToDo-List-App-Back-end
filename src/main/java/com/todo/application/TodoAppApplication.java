package com.todo.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@SpringBootApplication
@ComponentScan(basePackages = {"com.todo.application","com.todo.application.controller","com.todo.application.domain","com.todo.application.jwt","com.todo.application.jwt.resource","com.todo.application.repository","com.todo.application.registration"})
public class TodoAppApplication {
	
	
	public static void main(String[] args) {
		SpringApplication.run(TodoAppApplication.class, args);
	}
	
	

}
