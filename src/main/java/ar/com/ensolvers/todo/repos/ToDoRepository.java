package ar.com.ensolvers.todo.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ensolvers.todo.entities.ToDo;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo,Integer> {
    
}
