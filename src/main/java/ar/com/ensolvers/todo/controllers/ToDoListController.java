package ar.com.ensolvers.todo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ensolvers.todo.entities.ToDoList;
import ar.com.ensolvers.todo.model.request.InfoNewToDoList;
import ar.com.ensolvers.todo.model.response.GenericResponse;
import ar.com.ensolvers.todo.services.ToDoListService;
import ar.com.ensolvers.todo.services.ToDoListService.ValidationToDoList;

@RestController
public class ToDoListController {

    @Autowired
    ToDoListService service;


    @PostMapping("/todo-lists")
    public ResponseEntity<GenericResponse> create(@RequestBody InfoNewToDoList newToDoList) {

        GenericResponse response = new GenericResponse();

        ValidationToDoList result = service.validate(newToDoList.title);

        if (result == ValidationToDoList.OK) {
            ToDoList toDoList = service.create(newToDoList.title, newToDoList.tasks, newToDoList.folderId);

            response.isOk = true;
            response.message = "A new To-Do List has been created";
            response.id = toDoList.getToDoListId();

            return ResponseEntity.ok(response);
        }

        else {
            response.isOk = false;
            response.message = "Error(" + result.toString() + ")";

            return ResponseEntity.badRequest().body(response);
        }
    }
    


}

