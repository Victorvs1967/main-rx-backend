package com.vvs.mainrxbackend.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class AuthRouter {
  
  @Bean
  public RouterFunction<ServerResponse> authRouterFunction(AuthHandler authHandler) {
    return RouterFunctions
      .route(POST("/login").and(accept(APPLICATION_JSON)), authHandler::login)
      .andRoute(POST("/signup").and(accept(APPLICATION_JSON)), authHandler::signup);
  }
}
