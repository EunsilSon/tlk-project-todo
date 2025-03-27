package project.crud.todo.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachService {
    boolean save(List<MultipartFile> files, String groupId, Long createdBy);
    boolean delete(Long id);
}
