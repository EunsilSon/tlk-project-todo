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

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final int DEFAULT_TASK_SIZE = 20;
    public final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    public boolean create(TaskVO taskVO) {
        try {
            // 이런거 만들 때 귀찮자나
            // 이런 식으로 바꾸면 좀 맛있음
            // 클린 코드에 나오는 기법 중 하난데, 한번 공부해볼 것
            taskRepository.save(Task.from(taskVO));
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        taskRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> getMonthlyTask(int page, int year, int month) {
        Pageable pageable = PageRequest.of(page, DEFAULT_TASK_SIZE, Sort.by("id")); // 그거 알아 ? Pageable 자체도 Controller에서 받을 수 있다?
        Page<Task> tasks = taskRepository.findAllByYearAndMonth(year, month, pageable);
        return tasks.stream()
                .map(task -> new TaskDTO( // 이것도 FROM 쓸 수 있을듯
                        task.getId()
                        , task.getContent()
                        , task.getYear()
                        , task.getMonth()
                        , task.getDay()
                )).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> getDailyTask(int page, int year, int month, int day) {
        Pageable pageable = PageRequest.of(page, DEFAULT_TASK_SIZE, Sort.by("id"));
        Page<Task> tasks = taskRepository.findAllByYearAndMonthAndDay(year, month, day, pageable);
        return tasks.stream()
                .map(task -> new TaskDTO(// 이것도 FROM 쓸 수 있을듯
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