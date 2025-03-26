package project.crud.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.crud.todo.domain.entity.Attach;

import java.util.List;

@Repository
public interface AttachRepository extends JpaRepository<Attach, Long> {
    List<Attach> findByGroupId(String groupId);
    void deleteByGroupId(String groupId);
}
