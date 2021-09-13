package ar.com.ensolvers.todo.model.request;

import java.util.List;

import ar.com.ensolvers.todo.entities.ToDo;

public class InfoNewToDoList {
    
    public String title;

    public List<ToDo> tasks;
  
    public Integer folderId;
}
