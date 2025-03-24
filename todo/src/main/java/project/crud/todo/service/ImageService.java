package project.crud.todo.service;

import org.springframework.web.multipart.MultipartFile;
import project.crud.todo.domain.entity.Image;

import java.io.File;
import java.util.List;

public interface ImageService {
    Integer getCount(String groupId);
    List<File> getAll(String groupId);
    boolean save(List<MultipartFile> files, String groupId, Long createdBy);
    boolean delete(String groupId);
}
