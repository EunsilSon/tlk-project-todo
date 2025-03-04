package project.crud.todo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.crud.todo.domain.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Task getTaskById(Long id);

    @Query(nativeQuery = true, value = "select * from Task where date_format(date, '%Y-%m') like :date")
    Page<Task> getTasksByYearAndMonth(String date, Pageable pageable);

    @Query(nativeQuery = true, value = "select * from Task where date_format(date, '%Y-%m-%d') like :date")
    Page<Task> getTasksByDay(String date, Pageable pageable);
}
