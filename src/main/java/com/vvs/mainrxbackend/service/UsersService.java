package com.vvs.mainrxbackend.service;

import com.vvs.mainrxbackend.model.User;
import com.vvs.mainrxbackend.model.UserRole;
import com.vvs.mainrxbackend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class UsersService implements ReactiveUserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public Mono<UserDetails> findByUsername(String username) {
    return userRepository.findByUsername(username).cast(UserDetails.class);
  }

  public Mono<UserDetails> registerUser(String username, String password) {
    User user = new User();
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(password));
    user.setRole(UserRole.ROLE_USER);
    return userRepository.findByUsername(username)
                          .and(userRepository.save(user))
                          .cast(UserDetails.class);
  }

}
