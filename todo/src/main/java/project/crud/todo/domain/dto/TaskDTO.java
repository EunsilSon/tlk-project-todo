package project.crud.todo.domain.dto;

public class TaskDTO {
    private Long id;
    private String content;
    private Integer year;
    private Integer month;
    private Integer day;

    public TaskDTO(Long id, String content, Integer year, Integer month, Integer day) {
        this.id = id;
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
}
