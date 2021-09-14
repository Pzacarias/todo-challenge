package ar.com.ensolvers.todo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ar.com.ensolvers.todo.services.FolderService;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import ar.com.ensolvers.todo.entities.Folder;
import ar.com.ensolvers.todo.entities.User;
import ar.com.ensolvers.todo.model.request.InfoNewFolderRequest;
import ar.com.ensolvers.todo.model.response.GenericResponse;
import ar.com.ensolvers.todo.services.UserService;
import ar.com.ensolvers.todo.services.FolderService.ValidationFolder;

@RestController
public class FolderController {

    @Autowired
    FolderService service;

    @Autowired
    UserService userService;

    @PostMapping("/folders")
    public ResponseEntity<GenericResponse> create(@RequestBody InfoNewFolderRequest newFolder) {

        GenericResponse response = new GenericResponse();

        ValidationFolder result = service.validate(newFolder.title);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        if (result == ValidationFolder.OK) {
            Folder folder = service.create(newFolder.title, user);

            response.isOk = true;
            response.message = "A new Folder has been created";
            response.id = folder.getFolderId();

            return ResponseEntity.ok(response);
        }

        else {
            response.isOk = false;
            response.message = "Error(" + result.toString() + ")";

            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/folders/{id}")
    public ResponseEntity<GenericResponse> delete(@PathVariable Integer id) {

        GenericResponse response = new GenericResponse();
        if (service.validateFolderExists(id)) {
            service.delete(id);
            response.isOk = true;
            response.message = "The Folder has been deleted correctly.";
            return ResponseEntity.ok(response);

        } else {
            response.isOk = false;
            response.message = "The id that was entered is incorrect.";
            return ResponseEntity.badRequest().body(response);

        }

    }
    
}
