package project.crud.todo.domain.entity;

import jakarta.persistence.*;
import project.crud.todo.domain.vo.TaskVO;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDate scheduledDate;

    @Column(nullable = false)
    private String groupId;

    public Task() { }

    public Task(String content, Integer year, Integer month, Integer day, Long createdBy, LocalDateTime createdAt, String groupId) {
        this.content = content;
        this.year = year;
        this.month = month;
        this.day = day;
        this.scheduledDate = LocalDate.of(year, month, day);
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.groupId = groupId;
    }

    public static Task from(TaskVO taskVO) {
        return new Task(taskVO.getContent()
                , Integer.parseInt(taskVO.getYear())
                , Integer.parseInt(taskVO.getMonth())
                , Integer.parseInt(taskVO.getDay())
                , taskVO.getCreatedBy()
                , LocalDateTime.now()
                , taskVO.getGroupId());
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
    public LocalDate getScheduledDate() { return scheduledDate; }
    public String getGroupId() {
        return groupId;
    }
}