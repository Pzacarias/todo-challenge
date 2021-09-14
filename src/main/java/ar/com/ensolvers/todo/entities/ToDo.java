package ar.com.ensolvers.todo.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.lang.Nullable;

@Entity
@Table (name = "todo")
public class ToDo {

    @Id
    @Column(name = "todo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private Integer toDoId;

    private String text;

    private Boolean finished;

    @ManyToOne
    @JoinColumn(name = "todolist_id", referencedColumnName = "todolist_id")
    @JsonIgnore
    @Nullable
    private ToDoList toDoList;

    public Integer getToDoId() {
        return toDoId;
    }

    public void setToDoId(Integer toDoId) {
        this.toDoId = toDoId;
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
