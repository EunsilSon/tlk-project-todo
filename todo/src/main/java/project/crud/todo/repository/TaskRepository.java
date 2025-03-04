package project.crud.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.crud.todo.domain.entity.Task;

import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Task getTaskById(Long id);
}
