package project.crud.todo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.crud.todo.domain.entity.Attach;
import project.crud.todo.repository.AttachRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class AttachServiceImpl implements AttachService {
    Logger logger = LoggerFactory.getLogger(AttachServiceImpl.class);

    private final AttachRepository attachRepository;

    @Autowired
    public AttachServiceImpl(AttachRepository attachRepository) {
        this.attachRepository = attachRepository;
    }

    @Transactional
    @Override
    public boolean save(List<MultipartFile> files, String groupId, Long createdBy) {
        String path = getPath();

        for (MultipartFile file : files) {
            String targetName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            try {
                saveLocal(file, path, targetName);
                Attach attach = new Attach(
                        file.getOriginalFilename(),
                        targetName,
                        path + targetName,
                        file.getContentType(),
                        file.getSize(),
                        groupId,
                        createdBy
                );
                attachRepository.save(attach);
            } catch (Exception e) {
                logger.warn("Failed Save Attach {}", e.toString());
                throw new NoSuchElementException("Failed Save Attach: " + e);
            }
        }
        return true;
    }

    private void saveLocal(MultipartFile file, String path, String targetName) {
        try {
            file.transferTo(new File(path + targetName));
        } catch (IOException e) {
            logger.warn("Failed Save Image {}", e.toString());
            throw new RuntimeException(e);
        }
    }

    private String getPath() {
        String dir = "uploads";
        String path = System.getProperty("user.home")
                + File.separator
                + dir
                + File.separator;

        File f = new File(path);
        if (!f.exists()) {
            boolean created = f.mkdirs();
            if (!created) {
                logger.warn("Failed Create directory: {}", path);
            }
        }
        return path;
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        try {
            Attach attach = attachRepository.findById(id).orElseThrow(NoSuchElementException::new);
            attachRepository.delete(attach);
        } catch (Exception e) {
            logger.warn("Failed Delete Attach {}", e.toString());
            throw new NoSuchElementException("Failed Delete Attach: " + e);
        }
        return true;
    }
}
