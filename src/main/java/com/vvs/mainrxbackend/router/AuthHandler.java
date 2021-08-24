package com.vvs.mainrxbackend.router;

import com.vvs.mainrxbackend.config.JwtUtil;
import com.vvs.mainrxbackend.model.JwtResponse;
import com.vvs.mainrxbackend.model.User;
import com.vvs.mainrxbackend.service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class AuthHandler {

  private final static ResponseEntity<Object> UNAUTHORIZED = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  private final static ResponseEntity<Object> AUTHORIZED = ResponseEntity.status(HttpStatus.OK).build();

  @Autowired
  private UsersService usersService;
  @Autowired
  private JwtUtil jwtUtil;
  @Autowired
  private PasswordEncoder passwordEncoder;

  public Mono<ServerResponse> login(ServerRequest request) {
    Mono<?> response = request.bodyToMono(User.class)
        .flatMap(credentials -> usersService.findByUsername(credentials.getUsername()).cast(User.class)
                  .map(userDetails -> passwordEncoder.matches(credentials.getPassword(), userDetails.getPassword())
                      ? new JwtResponse(jwtUtil.generateToken(userDetails),
                                        userDetails.getId(), 
                                        userDetails.getUsername(),
                                        userDetails.getRole().name())
                      : UNAUTHORIZED)
                  .defaultIfEmpty(UNAUTHORIZED));
    return ServerResponse
            .ok()
            .contentType(APPLICATION_JSON)
            .body(response, JwtResponse.class);
  }

  public Mono<ServerResponse> signup(ServerRequest request) {
    Mono<?> user = request.bodyToMono(User.class)
        .flatMap(credentials -> usersService.registerUser(credentials.getUsername(), credentials.getPassword()).cast(User.class)
        .map(userDetails -> AUTHORIZED));
    return ServerResponse
            .ok()
            .contentType(APPLICATION_JSON)
            .body(user, User.class);
  }

}
