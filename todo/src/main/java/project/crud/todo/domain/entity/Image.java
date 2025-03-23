package project.crud.todo.domain.entity;

import jakarta.persistence.*;
import project.crud.todo.service.ImageServiceImpl;

import java.time.LocalDate;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String path;

    @Column(nullable = false, length = 100)
    private String originName;

    @Column(nullable = false, length = 100)
    private String contentType;

    @Column(nullable = false)
    private long size;

    @Column(nullable = false, length = 100)
    private String groupId;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Column(nullable = false)
    private Long createdBy;

    public Image() {

    }

    public Image(String path, String originName, String contentType, long size, String groupId, LocalDate createdAt, Long createdBy) {
        this.path = path;
        this.originName = originName;
        this.contentType = contentType;
        this.size = size;
        this.groupId = groupId;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
}
