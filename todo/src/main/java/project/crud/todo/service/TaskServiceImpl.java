package project.crud.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.crud.todo.domain.dto.TaskDTO;
import project.crud.todo.domain.entity.Image;
import project.crud.todo.domain.entity.Task;
import project.crud.todo.domain.vo.TaskVO;
import project.crud.todo.repository.ImageRepository;
import project.crud.todo.repository.TaskRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final int DEFAULT_TASK_SIZE = 20;
    private final TaskRepository taskRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ImageService imageService, ImageRepository imageRepository) {
        this.taskRepository = taskRepository;
        this.imageService = imageService;
        this.imageRepository = imageRepository;
    }

    @Override
    @Transactional
    public boolean create(TaskVO taskVO) {
        try {
            imageService.save(taskVO.getFiles(), taskVO.getGroupId(), taskVO.getCreatedBy());
            taskRepository.save(Task.from(taskVO));
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Task not found"));
        imageRepository.deleteByGroupId(task.getGroupId());
        taskRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> getMonthlyTask(int page, int year, int month) {
        Pageable pageable = PageRequest.of(page, DEFAULT_TASK_SIZE, Sort.by("id"));
        Page<Task> tasks = taskRepository.findAllByYearAndMonth(year, month, pageable);
        return getTaskDtoList(tasks);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> getDailyTask(int page, int year, int month, int day) {
        Pageable pageable = PageRequest.of(page, DEFAULT_TASK_SIZE, Sort.by("id"));
        Page<Task> tasks = taskRepository.findAllByYearAndMonthAndDay(year, month, day, pageable);
        return getTaskDtoList(tasks);
    }

    private List<TaskDTO> getTaskDtoList(Page<Task> tasks) {
        List<TaskDTO> list = new ArrayList<>();
        for (Task task : tasks) {
            List<Image> images = imageRepository.findByGroupId(task.getGroupId());
            list.add(new TaskDTO(task, images));
        }
        return list;
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

    private int getLastDay(int year, int month) {
        int[] lastDayByMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (month == 2) {
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                lastDayByMonth[1] = 29;
            }
        }
        return lastDayByMonth[month - 1];
    }
}