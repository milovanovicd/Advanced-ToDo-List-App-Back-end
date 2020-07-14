package com.todo.application.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.todo.application.domain.User;
import com.todo.application.repository.UserJpaRepository;

@Service
public class JwtInMemoryUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserJpaRepository userRepository;
	
//  static {
//    inMemoryUserList.add(new JwtUserDetails(2L, "dejan",
//            "$2a$10$WrPAfCSc/Ai5.r.l65bAjODs7C9zIE61rNUFyPS8vnNjdchhkg3lC", "ROLE_USER_2"));
//
//  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	  User user = userRepository.findByUsername(username);
//    Optional<JwtUserDetails> findFirst = inMemoryUserList.stream()
//        .filter(user -> user.getUsername().equals(username)).findFirst();
//
//    if (!findFirst.isPresent()) {
//      throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
//    }
//
//    return findFirst.get();
	  return new JwtUserDetails(user.getUsername(), user.getPassword());
  }

}


