package project.crud.todo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.crud.todo.domain.entity.Image;
import project.crud.todo.repository.ImageRepository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Transactional
    @Override
    public boolean save(List<MultipartFile> files, String groupId, Long createdBy) {
        for (MultipartFile image : files) {
            try {
                String path = saveLocal(image);
                Image imageEntity = new Image(path
                        , image.getOriginalFilename()
                        , image.getContentType()
                        , image.getSize()
                        , groupId
                        , createdBy);
                imageRepository.save(imageEntity);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    @Transactional
    @Override
    public boolean delete(String groupId) {
        //TODO: group id로 모두 삭제
        //TODO: 특정 이미지만 삭제
        return false;
    }

    @Transactional
    protected String saveLocal(MultipartFile image) {
        try {
            String fileName = image.getOriginalFilename(); //TODO: 파일명이 중복된다면?
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

    @Transactional(readOnly = true)
    @Override
    public Integer getCount(String groupId) {
        return 0;
    }

    @Transactional(readOnly = true)
    @Override
    public List<File> getAll(String groupId) {
        return List.of();
    }
}
