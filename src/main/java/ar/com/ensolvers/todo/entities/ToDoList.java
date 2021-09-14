package ar.com.ensolvers.todo.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table (name = "todolist")
public class ToDoList {

    @Id
    @Column(name = "todolist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Integer toDoListId;

    private String title;

    @OneToMany(mappedBy = "toDoList", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ToDo> tasks = new ArrayList <>();

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "folder_id", referencedColumnName = "folder_id")
    private Folder folder;

    
    public Integer getToDoListId() {
        return toDoListId;
    }

    public void setToDoListId(Integer toDoListId) {
        this.toDoListId = toDoListId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ToDo> getTasks() {
        return tasks;
    }

    public void setTasks(List<ToDo> tasks) {
        this.tasks = tasks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        
    }

    public void addTasks(ToDo toDo) {
        this.tasks.add(toDo);
        toDo.setToDoList(this);
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    
    
}
