package project.crud.todo.domain.vo;

import jakarta.validation.constraints.Size;

public class TaskVO {
    private Long id;
    private String content;

    public Long getId() {
        return id;
    }

    @Size(min = 1, max = 100, message = "입력 길이는 최소 1자, 최대 100자입니다.")
    public String getContent() {
        return content;
    }
}
