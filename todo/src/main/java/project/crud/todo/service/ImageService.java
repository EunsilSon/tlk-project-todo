package project.crud.todo.service;

import org.springframework.web.multipart.MultipartFile;
import project.crud.todo.domain.entity.Image;

import java.io.File;
import java.util.List;

public interface ImageService {
    boolean save(List<MultipartFile> files, String groupId, Long createdBy);
    boolean delete(Long groupId);
    List<File> getAll(String groupId);
}
