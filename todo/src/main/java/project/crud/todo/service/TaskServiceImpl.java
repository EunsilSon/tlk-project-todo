package project.crud.todo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.crud.todo.domain.dto.AttachDTO;
import project.crud.todo.domain.dto.TaskDTO;
import project.crud.todo.domain.entity.Attach;
import project.crud.todo.domain.entity.Task;
import project.crud.todo.domain.vo.TaskVO;
import project.crud.todo.repository.AttachRepository;
import project.crud.todo.repository.TaskRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    Logger logger = LoggerFactory.getLogger(AttachServiceImpl.class);

    private final TaskRepository taskRepository;
    private final AttachService attachService;
    private final AttachRepository attachRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, AttachService attachService, AttachRepository attachRepository) {
        this.taskRepository = taskRepository;
        this.attachService = attachService;
        this.attachRepository = attachRepository;
    }

    @Override
    @Transactional
    public boolean create(List<MultipartFile> files, TaskVO taskVO) {
        try {
            if (files != null && !files.isEmpty()) {
                attachService.save(files, taskVO.getGroupId(), taskVO.getCreatedBy());
            }
            taskRepository.save(Task.from(taskVO));
            return true;
        } catch(Exception e) {
            logger.error("Task could not be created: {}", e.toString());
            throw new RuntimeException("Task could not be created: " + e);
        }
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(NoSuchElementException::new);
        try {
            attachRepository.deleteByGroupId(task.getGroupId());
            taskRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            logger.error("Task could not be deleted: {}", e.toString());
            throw new RuntimeException("Task could not be deleted: " + e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> getMonthlyTask(int page, int year, int month, Pageable pageable) {
        return getTaskDTOs(taskRepository.findAllByYearAndMonth(year, month, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> getDailyTask(int page, int year, int month, int day, Pageable pageable) {
        return getTaskDTOs(taskRepository.findAllByYearAndMonthAndDay(year, month, day, pageable));
    }

    private List<TaskDTO> getTaskDTOs(Page<Task> tasks) {
        return tasks.stream()
                .map(task -> new TaskDTO(
                        task,
                        getAttaches(task)))
                .collect(Collectors.toList());
    }

    private List<AttachDTO> getAttaches(Task task) {
        List<Attach> attaches = attachRepository.findByGroupId(task.getGroupId());
        return attaches.stream()
                .map(attach -> new AttachDTO(
                        attach.getId(),
                        attach.getOriginName(),
                        attach.getTargetName(),
                        attach.getPath()))
                .collect(Collectors.toList());
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