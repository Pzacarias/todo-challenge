package ar.com.ensolvers.todo.entities;

import javax.persistence.*;

@Entity
@Table (name = "todo")
public class ToDo {

    @Id
    @Column(name = "todo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private Integer id;

    private String text;

    private Boolean finished;

    @ManyToOne
    @JoinColumn(name = "todolist_id", referencedColumnName = "todolist_id")
    private ToDoList toDoList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public ToDoList getToDoList() {
        return toDoList;
    }

    public void setToDoList(ToDoList toDoList) {
        this.toDoList = toDoList;
    }
    
    
}
