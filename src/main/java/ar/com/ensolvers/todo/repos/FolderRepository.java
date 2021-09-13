package ar.com.ensolvers.todo.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ensolvers.todo.entities.Folder;

@Repository
public interface FolderRepository extends JpaRepository <Folder, Integer>{
 
    Folder findByFolderId(Integer id);
}
