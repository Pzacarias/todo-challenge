package ar.com.ensolvers.todo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import ar.com.ensolvers.todo.entities.ToDoList;
import ar.com.ensolvers.todo.entities.User;
import ar.com.ensolvers.todo.model.request.InfoNewToDoList;
import ar.com.ensolvers.todo.model.response.GenericResponse;
import ar.com.ensolvers.todo.services.ToDoListService;
import ar.com.ensolvers.todo.services.UserService;
import ar.com.ensolvers.todo.services.ToDoListService.ValidationToDoList;

@RestController
public class ToDoListController {

    @Autowired
    ToDoListService service;

    @Autowired
    UserService userService;


    @PostMapping("/todo-lists")
    public ResponseEntity<GenericResponse> create(@RequestBody InfoNewToDoList newToDoList) {

        GenericResponse response = new GenericResponse();

        ValidationToDoList result = service.validate(newToDoList.title);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        if (result == ValidationToDoList.OK) {
            ToDoList toDoList = service.create(newToDoList.title, newToDoList.tasks, newToDoList.folderId, user);

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

    @GetMapping("/todo-lists")
    public ResponseEntity<List<ToDoList>> getToDoLists() {
        return ResponseEntity.ok(service.getAll());
    }
    
    @GetMapping("/todo-lists/{id}")
    public ResponseEntity<?> getToDoListById(@PathVariable Integer id) {
        GenericResponse response = new GenericResponse();
        if (!service.validateToDoListExists(id)) {
            response.isOk = false;
            response.message = "The id that was entered is incorrect.";
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(service.findByToDoListId(id));
    }


}

