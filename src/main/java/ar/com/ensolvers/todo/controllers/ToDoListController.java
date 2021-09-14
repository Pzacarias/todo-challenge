package ar.com.ensolvers.todo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import ar.com.ensolvers.todo.entities.Folder;
import ar.com.ensolvers.todo.entities.ToDoList;
import ar.com.ensolvers.todo.entities.User;
import ar.com.ensolvers.todo.model.request.FolderUpdateRequest;
import ar.com.ensolvers.todo.model.request.InfoNewToDoList;
import ar.com.ensolvers.todo.model.request.InfoUpdateToDoList;
import ar.com.ensolvers.todo.model.response.GenericResponse;
import ar.com.ensolvers.todo.services.FolderService;
import ar.com.ensolvers.todo.services.ToDoListService;
import ar.com.ensolvers.todo.services.UserService;
import ar.com.ensolvers.todo.services.ToDoListService.ValidateToDoListUpdate;
import ar.com.ensolvers.todo.services.ToDoListService.ValidationToDoList;

@RestController
public class ToDoListController {

    @Autowired
    ToDoListService service;

    @Autowired
    FolderService folderService;

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

    @PutMapping("/todo-lists/{id}")
    public ResponseEntity<GenericResponse> update(@PathVariable Integer id, @RequestBody InfoUpdateToDoList newInfo) {

        GenericResponse response = new GenericResponse();

        ValidateToDoListUpdate result = service.validate(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        if (result == ValidateToDoListUpdate.OK) {
            ToDoList toDoList = service.updateToDoList(id, newInfo.tasks, user);

            response.isOk = true;
            response.message = "The To-Do List was correctly edited.";
            response.id = toDoList.getToDoListId();

            return ResponseEntity.ok(response);
        }

        else {
            response.isOk = false;
            response.message = "Error(" + result.toString() + ")";

            return ResponseEntity.badRequest().body(response);
        }

    }

    @PutMapping("/todo-lists/{id}/folder")
    public ResponseEntity<GenericResponse> update(@PathVariable Integer id,
            @RequestBody FolderUpdateRequest infoFolder) {

        GenericResponse r = new GenericResponse();

        ToDoList toDoList = service.findByToDoListId(id);
        Folder folder = folderService.findByFolderId(infoFolder.folderId);
        toDoList.setFolder(folder);
        service.update(toDoList);   

        r.isOk = true;
        r.message = "The folder of the ToDo has been updated.";
        r.id = toDoList.getToDoListId();

        return ResponseEntity.ok(r);
    }

    @DeleteMapping("/todo-lists/{id}")
    public ResponseEntity<GenericResponse> delete(@PathVariable Integer id) {

        GenericResponse response = new GenericResponse();
        if (service.validateToDoListExists(id)) {
            service.delete(id);
            response.isOk = true;
            response.message = "The To-Do List has been deleted correctly.";
            return ResponseEntity.ok(response);

        } else {
            response.isOk = false;
            response.message = "The id that was entered is incorrect.";
            return ResponseEntity.badRequest().body(response);

        }

    }

}
