package project.crud.todo.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String getPath(String dir);
    boolean saveLocal(MultipartFile file, String path, String targetName);
    void deleteLocal(String path);
}
