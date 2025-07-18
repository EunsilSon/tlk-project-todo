package project.crud.todo.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import project.crud.todo.domain.dto.TaskDTO;
import project.crud.todo.domain.vo.TaskVO;

import java.util.List;

public interface TaskService {
    boolean create(List<MultipartFile> files, TaskVO taskVO);
    boolean delete(Long id);
    List<TaskDTO> getMonthlyTask(int page, int year, int month, Pageable pageable);
    List<TaskDTO> getDailyTask(int page, int year, int month, int day, Pageable pageable);
    int[] getTaskCount(int year, int month);
}