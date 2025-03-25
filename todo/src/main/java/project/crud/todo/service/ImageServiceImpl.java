package project.crud.todo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.crud.todo.domain.entity.Image;
import project.crud.todo.repository.ImageRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ImageServiceImpl implements ImageService {
    Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    private final ImageRepository imageRepository;
    private final S3UploadService s3UploadService;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository, S3UploadService s3UploadService) {
        this.imageRepository = imageRepository;
        this.s3UploadService = s3UploadService;
    }

    @Transactional
    @Override
    public boolean save(List<MultipartFile> files, String groupId, Long createdBy) {
        for (MultipartFile image : files) {
            try {
                String path = s3UploadService.saveFile(image);
                Image imageEntity = new Image(
                        path
                        , image.getOriginalFilename()
                        , image.getContentType()
                        , image.getSize()
                        , groupId
                        , createdBy
                );
                imageRepository.save(imageEntity);
                System.out.println("ImageServiceImpl: " + imageEntity.getS3Path());
            } catch (Exception e) {
                logger.error(e.getMessage());
                return false;
            }
        }
        return true;
    }

    /*
    @Transactional
    protected String saveLocal(MultipartFile image) {
        try {
            String fileName = image.getOriginalFilename();
            String path = System.getProperty("user.dir")
                    + File.separator
                    + "image"
                    + File.separator;

            File f = new File(path);
            if (!f.exists()) {
                f.mkdir();
            }

            image.transferTo(new File(path + fileName)); // 파일 객체의 경로와 이름으로 새 경로를 생성해 사용자의 로컬에 실제 업로드
            return path + fileName;
        } catch (IOException e) {
            return null;
        }
    }
    */

    @Transactional
    @Override
    public boolean delete(Long id) {
        try {
            Image image = imageRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Image not found"));
            imageRepository.delete(image);
            s3UploadService.deleteFile(image.getS3Path());
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
