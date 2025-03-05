package project.crud.todo.service;

import project.crud.todo.domain.dto.TaskDTO;
import project.crud.todo.domain.vo.TaskVO;

import java.util.List;

public interface TaskService {
    void create(TaskVO taskVO);

    void update(TaskVO taskVO);

    void delete(Long id);

    TaskDTO get(Long id);

    List<TaskDTO> getAllByYearAndMonth(int page, int year, int month);

    List<TaskDTO> getAllByDay(int page, int year, int month, int day);

}