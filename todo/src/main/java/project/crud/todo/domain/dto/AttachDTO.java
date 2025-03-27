package project.crud.todo.domain.dto;

public class AttachDTO {
    private Long id;
    private String originName;
    private String preSignedUrl;

    public AttachDTO(Long id, String originName, String preSignedUrl) {
        this.id = id;
        this.originName = originName;
        this.preSignedUrl = preSignedUrl;
    }

    public Long getId() {
        return id;
    }
    public String getOriginName() {
        return originName;
    }
    public String getPreSignedUrl() {
        return preSignedUrl;
    }
}
