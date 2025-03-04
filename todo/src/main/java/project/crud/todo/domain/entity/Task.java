package project.crud.todo.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String content;

    @Column(nullable = false)
    private LocalDate date;

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getDate() {
        return date;
    }
}
