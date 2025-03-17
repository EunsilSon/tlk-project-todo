package project.crud.todo.domain.dto;

import project.crud.todo.domain.entity.Task;

public record TaskDTO (
    Long id,
    String content,
    Integer year,
    Integer month,
    Integer day
) {
    public static TaskDTO from(Task task) {
        return new TaskDTO(task.getId()
                , task.getContent()
                , task.getYear()
                , task.getMonth()
                , task.getDay());
    }
}