package project.crud.todo.service;

import project.crud.todo.domain.dto.TaskDTO;
import project.crud.todo.domain.vo.TaskVO;

import java.util.List;

public interface TaskService {
    boolean create(TaskVO taskVO);
    boolean delete(Long id);
    List<TaskDTO> getMonthlyTask(int page, int year, int month);
    List<TaskDTO> getDailyTask(int page, int year, int month, int day);
    int[] getTaskCount(int year, int month);
}