package ar.com.ensolvers.todo.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table (name = "folder")
public class Folder {

    @Id
    @Column(name = "folder_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Integer id;

    private String title;

    @OneToMany(mappedBy = "todolist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ToDoList> toDoLists = new ArrayList <>();

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ToDoList> getToDoLists() {
        return toDoLists;
    }

    public void setToDoLists(List<ToDoList> toDoLists) {
        this.toDoLists = toDoLists;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addToDoLists(ToDoList toDoList){
        this.toDoLists.add(toDoList);
        toDoList.setFolder(this);
    }

    
    
}
