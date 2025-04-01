package project.crud.todo.service;

import org.springframework.web.multipart.MultipartFile;
import project.crud.todo.domain.entity.Attach;

import java.io.IOException;
import java.util.List;

public interface AttachService {
    List<Attach> save(List<MultipartFile> files, String groupId, Long createdBy) throws IOException;
    boolean delete(Long id);
}
