package project.crud.todo.domain.dto;

import project.crud.todo.domain.entity.Task;

import java.util.List;

public class TaskDTO {
    private Long id;
    private String content;
    private Integer year;
    private Integer month;
    private Integer day;
    private String groupId;
    private List<AttachDTO> attaches;

    public TaskDTO(Task task, List<AttachDTO> files) {
        this.id = task.getId();
        this.content = task.getContent();
        this.year = task.getYear();
        this.month = task.getMonth();
        this.day = task.getDay();
        this.groupId = task.getGroupId();
        this.attaches = files;
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
    public String getGroupId() { return groupId; }
    public List<AttachDTO> getAttaches() {
        return attaches;
    }
}