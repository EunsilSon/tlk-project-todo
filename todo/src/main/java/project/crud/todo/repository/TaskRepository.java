package project.crud.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.crud.todo.domain.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
}