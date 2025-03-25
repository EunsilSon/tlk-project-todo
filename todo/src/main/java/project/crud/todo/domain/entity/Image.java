package project.crud.todo.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String s3Path;

    @Column(nullable = false, length = 100)
    private String originName;

    @Column(nullable = false, length = 100)
    private String contentType;

    @Column(nullable = false)
    private long size;

    @Column(nullable = false, length = 100)
    private String groupId;

    @Column(nullable = false)
    private Long createdBy;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Image() {

    }

    public Image(String s3Path, String originName, String contentType, long size, String groupId, Long createdBy) {
        this.s3Path = s3Path;
        this.originName = originName;
        this.contentType = contentType;
        this.size = size;
        this.groupId = groupId;
        this.createdBy = createdBy;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }
    public String getS3Path() {
        return s3Path;
    }
    public String getOriginName() {
        return originName;
    }
    public String getContentType() {
        return contentType;
    }
    public long getSize() {
        return size;
    }
    public String getGroupId() {
        return groupId;
    }
    public Long getCreatedBy() {
        return createdBy;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
