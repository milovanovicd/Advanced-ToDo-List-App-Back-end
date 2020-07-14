package com.todo.application.jwt.resource;

import java.io.Serializable;

public class  JwtTokenRequest implements Serializable {
  
  private static final long serialVersionUID = -5616176897013108345L;

  private String username;
    private String password;
    
//    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJpbjI4bWludXRlcyIsImV4cCI6MTU5MDIyNjMwOSwiaWF0IjoxNTg5NjIxNTA5fQ.WTAjm9aZP6MkQFByihkzhRFViFF6mDhwrZ1yZC-jayQ9oDz8qyd0K5YMQ1lm1LCb-W6bkGheGotZi9VWeRqD-Q"

    public JwtTokenRequest() {
        super();
    }

    public JwtTokenRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

