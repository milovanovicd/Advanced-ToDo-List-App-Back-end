	package com.todo.application.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class ToDo {
	
	@Id
	@GeneratedValue
	private long id;
	
	@OneToOne
	@JoinColumn(name ="processId")
	private Process process;

	@OneToOne
	@JoinColumn(name ="username")
	private User user;
	private String description;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date targetDate;
	private Priority priority;
	private Status status;
	private Type type;

	public ToDo() {
		this.status = Status.JUST_CREATED;
	}
	
	public ToDo(long id, Process process, User user, String description, Date targetDate, Priority priority,Type type) {
		super();
		this.id = id;
		this.process = process;
		this.user = user;
		this.description = description;
		this.targetDate = targetDate;
		this.priority = priority;
		this.status = Status.JUST_CREATED;
		this.type = type;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public User getUsername() {
		return user;
	}
	public void setUsername(User user) {
		this.user = user;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getTargetDate() {
		return targetDate;
	}
	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}

	public Priority getPriority() {
		return priority;
	}
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	
	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ToDo other = (ToDo) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	
}
