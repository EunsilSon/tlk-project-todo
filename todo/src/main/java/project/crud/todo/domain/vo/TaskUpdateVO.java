package project.crud.todo.domain.vo;

import jakarta.validation.constraints.Size;

public class TaskUpdateVO {
    private Long id;
    @Size(min = 1, max = 100, message = "입력 길이는 최소 1자, 최대 100자입니다.")
    private String content;
    private String date;
    private Integer year;
    private Integer month;
    private Integer day;

    public Long getId() { return id; }
    public String getContent() {
        return content;
    }
}
