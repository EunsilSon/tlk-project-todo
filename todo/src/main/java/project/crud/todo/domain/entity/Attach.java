package project.crud.todo.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Attach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String originName;

    @Column(nullable = false, length = 100)
    private String targetName;

    @Column(nullable = false, length = 100)
    private String path;

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

    public Attach() {

    }

    public Attach(String originName, String targetName, String path, String contentType, long size, String groupId, Long createdBy) {
        this.originName = originName;
        this.targetName = targetName;
        this.path = path;
        this.contentType = contentType;
        this.size = size;
        this.groupId = groupId;
        this.createdBy = createdBy;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }
    public String getOriginName() {
        return originName;
    }
    public String getTargetName() { return targetName; }
    public String getPath() { return path; }
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
