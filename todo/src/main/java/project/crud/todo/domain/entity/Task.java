package project.crud.todo.domain.entity;

import jakarta.persistence.*;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String content;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer month;

    @Column(nullable = false)
    private Integer day;

    public Task() { }

    public Task(String content, Integer year, Integer month, Integer day) {
        this.content = content;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Long getId() {
        return id;
    }
    public String getContent() {
        return content;
    }
    public Integer getYear() {
        return year;
    }
    public Integer getMonth() {
        return month;
    }
    public Integer getDay() {
        return day;
    }
    public void updateContent(String content) {
        this.content = content;
    }

}
