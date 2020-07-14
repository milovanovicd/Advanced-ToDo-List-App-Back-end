package com.todo.application.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Process {
	@Id
	@GeneratedValue
	private long processId;
	@OneToMany
	private List<ToDo> toDoList;
	@OneToOne
	@JoinColumn(name ="username")
	private User user;
	private String name;
	private Priority priority;
	
	public Process() {
		// TODO Auto-generated constructor stub
	}

	public long getProcessId() {
		return processId;
	}

	public void setProcessId(long processId) {
		this.processId = processId;
	}

	public List<ToDo> getToDoList() {
		return toDoList;
	}

	public void setToDoList(List<ToDo> toDoList) {
		this.toDoList = toDoList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	
	
	

}
