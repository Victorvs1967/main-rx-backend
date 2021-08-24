package com.vvs.mainrxbackend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

import java.time.Instant;

@Data
@Document("todo")
public class ToDo {

  @Id
  private String id;
  private String description;
  private Instant created;
  private Instant modified;
  private boolean completed;
  private String userId;

  public ToDo() {
    this.created = Instant.now();
    this.modified = Instant.now();
  }

  public ToDo(String description, String userId) {
    this();
    this.description = description;
    this.userId = userId;
  }

  public ToDo(String description, boolean completed, String userId) {
    this();
    this.description = description;
    this.completed = completed;
    this.userId = userId;
  }
  
}
