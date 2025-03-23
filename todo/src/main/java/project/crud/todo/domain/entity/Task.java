package project.crud.todo.domain.entity;

import jakarta.persistence.*;
import project.crud.todo.domain.vo.TaskVO;

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

    @Column(nullable = false)
    private Long createdBy;

    public Task() { }

    public Task(String content, Integer year, Integer month, Integer day, Long createdBy) {
        this.content = content;
        this.year = year;
        this.month = month;
        this.day = day;
        this.createdBy = createdBy;
    }

    public static Task from(TaskVO taskVO) {
        return new Task(taskVO.getContent()
                , Integer.parseInt(taskVO.getYear())
                , Integer.parseInt(taskVO.getMonth())
                , Integer.parseInt(taskVO.getDay())
                , taskVO.getCreatedBy());
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

}
