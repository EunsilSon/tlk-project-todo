package project.crud.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.crud.todo.repository.TaskRepository;

@Service
public class TaskService {
    public final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public boolean test() {
        return true;
    }
}
