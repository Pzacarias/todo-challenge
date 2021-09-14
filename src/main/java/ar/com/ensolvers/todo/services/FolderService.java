package ar.com.ensolvers.todo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ensolvers.todo.entities.Folder;
import ar.com.ensolvers.todo.entities.User;
import ar.com.ensolvers.todo.repos.FolderRepository;


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

    public Folder findByFolderId(Integer folderId) {
        return repo.findByFolderId(folderId);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public boolean validateFolderExists(Integer id) {
        Folder folder = repo.findByFolderId(id);
        if (folder != null) {
            return true;
        } else
            return false;

    }
    
}
