package com.vvs.mainrxbackend.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class ToDoRouter {
  
  @Bean
  public RouterFunction<ServerResponse> monoRouterFunction(ToDoHandler todoHandler) {
    return RouterFunctions
      .route(GET("/todo/{id}").and(accept(MediaType.APPLICATION_JSON)), todoHandler::getToDo)
      .andRoute(GET("/todo").and(accept(MediaType.APPLICATION_JSON)), todoHandler::getToDos)
      .andRoute(POST("/todo").and(accept(MediaType.APPLICATION_JSON)), todoHandler::newToDo)
      .andRoute(PUT("/todo").and(accept(MediaType.APPLICATION_JSON)), todoHandler::updateToDo)
      .andRoute(DELETE("/todo/{id}").and(accept(MediaType.APPLICATION_JSON)), todoHandler::deleteTodo);
  }

}
