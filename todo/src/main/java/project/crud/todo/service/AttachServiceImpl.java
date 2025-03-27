package project.crud.todo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.crud.todo.domain.entity.Attach;
import project.crud.todo.repository.AttachRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class AttachServiceImpl implements AttachService {
    Logger logger = LoggerFactory.getLogger(AttachServiceImpl.class);

    private final AttachRepository attachRepository;
    private final S3UploadServiceImpl s3UploadServiceImpl;

    @Autowired
    public AttachServiceImpl(AttachRepository attachRepository, S3UploadServiceImpl s3UploadServiceImpl) {
        this.attachRepository = attachRepository;
        this.s3UploadServiceImpl = s3UploadServiceImpl;
    }

    @Transactional
    @Override
    public boolean save(List<MultipartFile> files, String groupId, Long createdBy) {
        for (MultipartFile file : files) {
            String key = "uploads/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

            try {
                s3UploadServiceImpl.saveFile(file, key);
                Attach attach = new Attach(
                        key
                        , file.getOriginalFilename()
                        , file.getContentType()
                        , file.getSize()
                        , groupId
                        , createdBy
                );
                attachRepository.save(attach);
            } catch (Exception e) {
                logger.error(e.getMessage());
                return false;
            }
        }
        return true;
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        try {
            Attach attach = attachRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Attach not found"));
            attachRepository.delete(attach);
            s3UploadServiceImpl.deleteFile(attach.getS3Key());
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return false;
        }
        return true;
    }
}
