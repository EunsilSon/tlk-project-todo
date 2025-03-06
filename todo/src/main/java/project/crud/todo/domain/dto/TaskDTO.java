package project.crud.todo.domain.dto;

import java.time.LocalDate;

public class TaskDTO {
    public Long id;
    public String content;
    public LocalDate date;

    public TaskDTO(Long id, String content, LocalDate date) {
        this.id = id;
        this.content = content;
        this.date = date;
    }
}
