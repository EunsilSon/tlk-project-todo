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

    @Query(nativeQuery = true, value = "select * from Task where year(date) = :year and month(date) = :month")
    Page<Task> getTasksByYearAndMonth(int year, int month, Pageable pageable);

    @Query(nativeQuery = true, value = "select * from Task where year(date) = :year and month(date) = :month and day(date) = :day")
    Page<Task> getTasksByDay(int year, int month, int day, Pageable pageable);

    @Query(nativeQuery = true, value = "select count(*) from Task where year(date) = :year and month(date) = :month and day(date) = :day")
    int countByYearAndMonthAndDay(int year, int month, int day);
}
