package project.crud.todo.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface FileService {
    Integer getCount(String groupId);
    List<File> getAll(String groupId);
    boolean upload(MultipartFile file);
    boolean delete(String groupId);
}
