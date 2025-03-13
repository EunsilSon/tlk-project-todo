package project.crud.todo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.crud.todo.domain.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findAllByYearAndMonth(int year, int month, Pageable pageable);
    Page<Task> findAllByYearAndMonthAndDay(int year, int month, int day, Pageable pageable);
    Integer countByYearAndMonthAndDay(int year, int month, int day);
}