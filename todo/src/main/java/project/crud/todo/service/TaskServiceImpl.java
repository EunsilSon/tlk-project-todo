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
import project.crud.todo.domain.vo.TaskVO;
import project.crud.todo.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    public final int DEFAULT_TASK_SIZE = 5;
    public final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    public void create(TaskVO taskVO) {
        taskRepository.save(new Task(taskVO.getContent(), taskVO.getDate()));
    }

    @Override
    @Transactional
    public void update(TaskVO taskVO) {
        Task task = taskRepository.findById(taskVO.getId())
                .orElseThrow(() -> new NoSuchElementException("Task not found"));
        task.updateContent(taskVO.getContent());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Task not found");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TaskDTO get(Long id) {
        Optional<Task> task = Optional.ofNullable(taskRepository.getTaskById(id));
        if (task.isPresent()) {
            return new TaskDTO(
                    task.get().getId()
                    , task.get().getContent()
                    , task.get().getDate()
            );
        } else {
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
                        , task.getDate()
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
                        , task.getDate()
                )).collect(Collectors.toList());
    }

    @Override
    public int[] getTaskCountByYearAndMonth(int year, int month) {
        int lastDay;
        if (month == 2) {
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                lastDay = 29;
            } else {
                lastDay = 28;
            }
        } else if (month == 1 || month == 4 || month == 6 || month == 9 || month == 11) {
            lastDay = 31;
        } else {
            lastDay = 30;
        }

        int[] countArr = new int[lastDay + 1];
        countArr[0] = -1;
        for (int i = 1; i <= lastDay; i++) {
            countArr[i] = taskRepository.countByYearAndMonthAndDay(year, month, i);
        }
        return countArr;
    }
}