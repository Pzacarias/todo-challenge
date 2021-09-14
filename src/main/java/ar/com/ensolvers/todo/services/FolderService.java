package ar.com.ensolvers.todo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ensolvers.todo.entities.Folder;
import ar.com.ensolvers.todo.entities.ToDoList;
import ar.com.ensolvers.todo.entities.User;
import ar.com.ensolvers.todo.repos.FolderRepository;
import ar.com.ensolvers.todo.services.ToDoListService.ValidationToDoList;

@Service
public class FolderService {

    @Autowired
    FolderRepository repo;

    public Folder findFolder(Integer folderId) {

        Folder folder = repo.findByFolderId(folderId);
        return folder;
    }

    public ValidationFolder validate(String title) {

        if (validateTitle(title))
            return ValidationFolder.ERROR_TITLE_EXISTS_ALREADY;

        return ValidationFolder.OK;
    }


    private boolean validateTitle(String title) {
        Folder folder = repo.findByTitle(title);
        if (folder != null) {
            return true;
        }
        return false;
    }

    public enum ValidationFolder {
        OK, ERROR_TITLE_EXISTS_ALREADY
    }

    public Folder create(String title, User user) {

        Folder folder = new Folder();
        folder.setTitle(title);
        folder.setUser(user);       

        return repo.save(folder);
    }

}
