package project.crud.todo.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface ImageService {
    Integer getCount(String groupId);
    List<File> getAll(String groupId);
    boolean save(MultipartFile image, String groupId, Long createdBy);
    boolean delete(String groupId);
}
