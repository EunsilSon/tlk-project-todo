package project.crud.todo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {
    Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    private final String FILE_UPLOAD_PATH = "user.dir";

    @Override
    public String getPath(String dir) {
        String path = System.getProperty(FILE_UPLOAD_PATH)
                + File.separator
                + dir
                + File.separator;

        File f = new File(path);
        if (!f.exists()) {
            boolean created = f.mkdirs();
            if (!created) {
                logger.warn("[Fail] Created directory: {}", path);
            }
            logger.info("[Success] Created Directory: {}", path);
        }
        return path;
    }

    @Override
    public boolean saveLocal(MultipartFile file, String path, String targetName) {
        try {
            file.transferTo(new File(path + targetName));
            logger.info("[Success] Save attach in local: {}", path);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteLocal(String path) {
        try {
            File file = new File(System.getProperty(FILE_UPLOAD_PATH) + File.separator + path);
            if (file.exists()) {
                if (file.delete()) {
                    logger.info("[Success] Delete attach in local: {}", path);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Attach delete in local: " + e);
        }
    }
}
