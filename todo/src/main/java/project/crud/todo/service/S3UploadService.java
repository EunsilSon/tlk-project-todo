package project.crud.todo.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3UploadService {
    boolean saveFile(MultipartFile file, String key);
    void deleteFile(String key);
    String generatePreSignedUrl(String key);
}
