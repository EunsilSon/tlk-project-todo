package project.crud.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.File;
import java.util.UUID;

@Service
public class S3UploadService {
    @Value("${spring.cloud.s3.bucket}")
    private String BUCKET_NAME;

    private final S3Client s3Client;

    @Autowired
    public S3UploadService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    /**
     * S3 파일 업로드
     * @param multipartFile 이미지 파일
     * @return S3 업로드 된 이미지 파일의 key
     */
    @Transactional
    public String saveFile(MultipartFile multipartFile) {
        String key = "uploads/" + UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(key)
                    .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromFile((File) multipartFile));

            return "https://" + BUCKET_NAME + ".s3." + Region.AP_NORTHEAST_2.id() + ".amazonaws.com/" + multipartFile.getOriginalFilename();
        } catch (S3Exception e) {
            return e.getMessage();
        }
    }

    @Transactional
    public void deleteFile(String key) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(key)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
        } catch (S3Exception e) {
            e.printStackTrace();
        }
    }
}
