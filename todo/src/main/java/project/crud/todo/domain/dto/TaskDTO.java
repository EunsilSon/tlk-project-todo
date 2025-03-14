package project.crud.todo.domain.dto;

public record TaskDTO (
    Long id,
    String content,
    Integer year,
    Integer month,
    Integer day
) { }