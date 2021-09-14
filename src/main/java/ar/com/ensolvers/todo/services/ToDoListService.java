package ar.com.ensolvers.todo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ensolvers.todo.entities.*;
import ar.com.ensolvers.todo.repos.ToDoListRepository;

@Service
public class ToDoListService {

    @Autowired
    ToDoListRepository repo;

    @Autowired
    FolderService folderService;


    public ToDoList create(String title, List<ToDo> tasks, Integer folderId, User user) {

        ToDoList toDoList = new ToDoList();
        toDoList.setTitle(title);

        if (folderId != null){
            Folder folder = folderService.findFolder(folderId);
            toDoList.setFolder(folder);
            user.addFolder(folder);
            
        }
        else{
            toDoList.setFolder(null);
        }

        for (ToDo toDo: tasks) {
           toDoList.addTasks(toDo);
        }

        user.addToDoList(toDoList);
                              
        return repo.save(toDoList);
    }


   public enum ValidationToDoList{
       OK, ERROR_TITLE_EXISTS_ALREADY
   }

    public ValidationToDoList validate(String title) {

        if (validateTitle(title))
            return ValidationToDoList.ERROR_TITLE_EXISTS_ALREADY;

        return ValidationToDoList.OK;
    }


    private boolean validateTitle(String title) {
        ToDoList toDoList = repo.findByTitle(title);
        if (toDoList != null ){
            return true;
        }
        return false;
    }


    public List<ToDoList> getAll() {
        return repo.findAll();
    }


    public boolean validateToDoListExists(Integer id) {
        ToDoList toDoList = repo.findByToDoListId(id);
        if (toDoList != null) {
            return true;
        } else
            return false;

    }


    public ToDoList findByToDoListId(Integer id) {
        return repo.findByToDoListId(id);
    }

  
}