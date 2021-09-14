package ar.com.ensolvers.todo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

import ar.com.ensolvers.todo.entities.ToDo;
import ar.com.ensolvers.todo.model.request.InfoNewToDo;
import ar.com.ensolvers.todo.model.response.GenericResponse;
import ar.com.ensolvers.todo.services.ToDoService;

@RestController
public class ToDoController {

    @Autowired
    ToDoService service;

    @PostMapping("/todo")
    public ResponseEntity<GenericResponse> create(@RequestBody InfoNewToDo newToDo) {

        GenericResponse response = new GenericResponse();

        ToDo toDo = service.create(newToDo.text, newToDo.toDoListId);

        response.isOk = true;
        response.message = "A new To-Do has been created";
        response.id = toDo.getToDoId();

        return ResponseEntity.ok(response);

    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<?> getToDoListById(@PathVariable Integer id) {
        GenericResponse response = new GenericResponse();
        if (!service.validateToDoExists(id)) {
            response.isOk = false;
            response.message = "The id that was entered is incorrect.";
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(service.findByToDoId(id));
    }

}