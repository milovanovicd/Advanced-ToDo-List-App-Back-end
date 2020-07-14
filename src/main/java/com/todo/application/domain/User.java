package com.todo.application.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	private String username;
	private String password;
	private String email;
	private boolean admin;
	
	@Column(name = "enabled")
    private boolean enabled;


	public User() {
		// TODO Auto-generated constructor stub
//		super();
        this.enabled=false;
	}

	public User(String username, String password, String email, boolean admin) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.admin = admin;
		this.enabled=false;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	

}
