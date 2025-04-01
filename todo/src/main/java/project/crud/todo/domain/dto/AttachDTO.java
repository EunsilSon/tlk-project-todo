package project.crud.todo.domain.dto;

public class AttachDTO {
    private Long id;
    private String originName;
    private String targetName;
    private String path;

    public AttachDTO(Long id, String originName, String targetName, String path) {
        this.id = id;
        this.originName = originName;
        this.targetName = targetName;
        this.path = path;
    }

    public Long getId() {
        return id;
    }
    public String getOriginName() {
        return originName;
    }
    public String getTargetName() { return targetName; }
    public String getPath() {
        return path;
    }
}
