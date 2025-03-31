package project.crud.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.crud.todo.domain.entity.Attach;

import java.util.List;

@Repository
public interface AttachRepository extends JpaRepository<Attach, Long> {
    List<Attach> findByGroupId(String groupId);

    @Modifying
    @Query(value = "DELETE  FROM attach WHRER group_id = :groupId", nativeQuery = true)
    int deleteByGroupId(String groupId);
}
