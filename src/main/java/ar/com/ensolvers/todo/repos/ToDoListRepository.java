package ar.com.ensolvers.todo.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ensolvers.todo.entities.ToDoList;

@Repository
public interface ToDoListRepository extends JpaRepository<ToDoList, Integer>{
    
    ToDoList findByTitle (String title);
}
