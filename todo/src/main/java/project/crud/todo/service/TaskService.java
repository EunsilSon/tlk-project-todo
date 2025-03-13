package project.crud.todo.service;

import project.crud.todo.domain.dto.TaskDTO;
import project.crud.todo.domain.vo.TaskUpdateVO;
import project.crud.todo.domain.vo.TaskVO;

import java.util.List;

public interface TaskService {
    boolean create(TaskVO taskVO);

    boolean update(TaskUpdateVO taskUpdateVO);

    boolean delete(Long id);

    List<TaskDTO> getAllByYearAndMonth(int page, int year, int month);

    List<TaskDTO> getAllByDay(int page, int year, int month, int day);

    int[] getTaskCount(int year, int month);

    int getLastDay(int year, int month);
}