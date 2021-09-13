package ar.com.ensolvers.todo.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ensolvers.todo.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    
    User findByUsername(String username);
    User findByEmail(String email);
}
