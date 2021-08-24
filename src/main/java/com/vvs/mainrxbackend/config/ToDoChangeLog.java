package com.vvs.mainrxbackend.config;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.vvs.mainrxbackend.model.ToDo;
import com.vvs.mainrxbackend.repository.ToDoRepository;

@ChangeLog
public class ToDoChangeLog {

  @ChangeSet(order = "001", id = "todoDB", author = "vvs")
  public void todoDB(ToDoRepository toDoRepository) {
    toDoRepository
      .save(new ToDo("Create Todo Item", "1"))
      .subscribe();
  }

}
