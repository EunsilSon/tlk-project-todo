package project.crud.todo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    public Integer getCount() {
        return getCount(null);
    }

    @Override
    public Integer getCount(String groupId) {
        return 0;
    }

    @Override
    public List<File> getAll(String groupId) {
        return List.of();
    }

    @Override
    public boolean upload(MultipartFile file) {
        return false;
    }

    @Override
    public boolean delete(String groupId) {
        return false;
    }
}
