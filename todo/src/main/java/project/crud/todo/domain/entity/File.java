package project.crud.todo.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String path;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String content_type;

    @Column(nullable = false, length = 100)
    private Long size;

    @Column(nullable = false, length = 100)
    private String groupId;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Column(nullable = false)
    private Long createdBy;
}
