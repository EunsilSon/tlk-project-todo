package project.crud.todo.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String content;

    private LocalDate date;

    public Task() {

    }

    public Task(String content) {
        this.content = content;
        this.date = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
