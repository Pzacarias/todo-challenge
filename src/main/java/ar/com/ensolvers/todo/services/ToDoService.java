package ar.com.ensolvers.todo.services;

import org.springframework.stereotype.Service;

import ar.com.ensolvers.todo.entities.ToDo;
import ar.com.ensolvers.todo.entities.ToDoList;
import ar.com.ensolvers.todo.repos.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ToDoService {

    @Autowired
    ToDoRepository repo;

    @Autowired
    ToDoListService toDoListService;

    public ToDo create(String text, Integer id) {
        ToDo toDo = new ToDo();
        toDo.setText(text);
        toDo.setFinished(false);
        if (id != null) {
            ToDoList toDoList = toDoListService.findByToDoListId(id);
            toDo.setToDoList(toDoList);
        }

        return repo.save(toDo);
    }

    public boolean validateToDoExists(Integer id) {
        ToDo toDo = repo.findByToDoId(id);
        if (toDo != null) {
            return true;
        } else
            return false;

    }

    public ToDo findByToDoId(Integer id) {
        return repo.findByToDoId(id);
    }

    

}
