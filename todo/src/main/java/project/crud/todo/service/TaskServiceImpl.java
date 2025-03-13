package project.crud.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.crud.todo.domain.dto.TaskDTO;
import project.crud.todo.domain.entity.Task;
import project.crud.todo.domain.vo.TaskUpdateVO;
import project.crud.todo.domain.vo.TaskVO;
import project.crud.todo.repository.TaskRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    public final int DEFAULT_TASK_SIZE = 20;
    public final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    public boolean create(TaskVO taskVO) {
        try {
            taskRepository.save(new Task(taskVO.getContent(), taskVO.getYear(), taskVO.getMonth(), taskVO.getDay()));
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    @Transactional
    public boolean update(TaskUpdateVO taskUpdateVO) {
        Task task = taskRepository.findById(taskUpdateVO.getId())
                .orElseThrow(() -> new NoSuchElementException("Task not found"));
        task.updateContent(taskUpdateVO.getContent());
        return true;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        try {
            taskRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new NoSuchElementException("Task not found");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> getAllByYearAndMonth(int page, int year, int month) {
        Pageable pageable = PageRequest.of(page, DEFAULT_TASK_SIZE, Sort.by("date"));
        Page<Task> tasks = taskRepository.getTasksByYearAndMonth(year, month, pageable);
        return tasks.stream()
                .map(task -> new TaskDTO(
                        task.getId()
                        , task.getContent()
                        , task.getYear()
                        , task.getMonth()
                        , task.getDay()
                )).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> getAllByDay(int page, int year, int month, int day) {
        Pageable pageable = PageRequest.of(page, DEFAULT_TASK_SIZE, Sort.by("date"));
        Page<Task> tasks = taskRepository.getTasksByDay(year, month, day, pageable);
        return tasks.stream()
                .map(task -> new TaskDTO(
                        task.getId()
                        , task.getContent()
                        , task.getYear()
                        , task.getMonth()
                        , task.getDay()
                )).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public int[] getTaskCount(int year, int month) {
        int lastDay = getLastDay(year, month);
        int[] countArr = new int[lastDay + 1];
        countArr[0] = -1;
        for (int i = 1; i <= lastDay; i++) {
            countArr[i] = taskRepository.countByYearAndMonthAndDay(year, month, i);
        }
        return countArr;
    }

    public int getLastDay(int year, int month) {
        int[] lastDayByMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (month == 2) {
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                lastDayByMonth[1] = 29;
            }
        }
        return lastDayByMonth[month-1];
    }
}