package project.crud.todo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import project.crud.todo.domain.entity.Attach;
import project.crud.todo.repository.AttachRepository;

import java.io.File;
import java.util.*;

@Service
public class AttachServiceImpl implements AttachService {
    Logger logger = LoggerFactory.getLogger(AttachServiceImpl.class);

    private final AttachRepository attachRepository;
    private final FileService fileService;

    @Autowired
    public AttachServiceImpl(AttachRepository attachRepository, FileService fileService) {
        this.attachRepository = attachRepository;
        this.fileService = fileService;
    }

    @Override
    public List<Attach> save(List<MultipartFile> files, String groupId, Long createdBy) {
        String dir = "attaches";
        String path = fileService.getPath(dir);
        List<Attach> attaches = new ArrayList<>();

        for (MultipartFile file : files) {
            String originalName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String targetName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            try {
                if (fileService.saveLocal(file, path, targetName)) {
                    Attach attach = new Attach(
                            originalName,
                            targetName,
                            dir + File.separator + targetName,
                            file.getContentType(),
                            file.getSize(),
                            groupId,
                            createdBy
                    );
                    attachRepository.save(attach);
                    attaches.add(attach);
                }
                logger.info("[Success] group_id: {} - Save completed.", groupId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return attaches;
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        Attach attach = attachRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Attach not found"));
        fileService.deleteLocal(attach.getPath());
        attachRepository.deleteById(id);
        return true;
    }
}
