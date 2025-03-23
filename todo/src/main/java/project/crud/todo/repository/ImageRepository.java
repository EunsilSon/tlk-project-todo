package project.crud.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.crud.todo.domain.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
