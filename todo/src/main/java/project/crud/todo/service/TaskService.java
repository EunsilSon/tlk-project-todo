package project.crud.todo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.crud.todo.domain.dto.TaskDTO;
import project.crud.todo.domain.entity.Task;
import project.crud.todo.domain.vo.TaskVO;
import project.crud.todo.repository.TaskRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class TaskService {
    public final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public void create(TaskVO taskVO) {
        taskRepository.save(new Task(taskVO.getContent()));
    }

    @Transactional
    public void update(TaskVO taskVO) {
        Task task = taskRepository.findById(taskVO.getId()).orElseThrow(() -> new NoSuchElementException("Task not found"));
        task.setContent(taskVO.getContent());
    }

    @Transactional
    public void delete(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Task not found");
        }
    }

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
}