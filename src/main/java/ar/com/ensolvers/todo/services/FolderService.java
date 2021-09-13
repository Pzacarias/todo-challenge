package ar.com.ensolvers.todo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ensolvers.todo.entities.Folder;
import ar.com.ensolvers.todo.repos.FolderRepository;

@Service
public class FolderService {

    @Autowired
    FolderRepository repo;


    public Folder findFolder(Integer folderId) {
        Folder folder = repo.findByFolderId(folderId);

        return folder;

    }


    
}
