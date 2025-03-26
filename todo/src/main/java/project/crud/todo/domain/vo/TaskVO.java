package project.crud.todo.domain.vo;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class TaskVO {
    @Size(min = 1, max = 100, message = "입력 길이는 최소 1자, 최대 100자입니다.")
    private String content;

    @Pattern(regexp = "^(19[0-9]{2}|20[0-4][0-9]|2050)$", message = "연도는 4자리입니다.")
    private String year;

    @Pattern(regexp = "^(0?[1-9]|1[0-2])$", message = "월은 최소 1자리, 최대 2자리입니다.")
    private String month;

    @Pattern(regexp = "^(0?[1-9]|[12][0-9]|3[01])$", message = "일자는 최소 1자리, 최대 2자리입니다.")
    private String day;

    private Long createdBy;
    private String groupId;

    public String getContent() {
        return content;
    }
    public String getYear() {
        return year;
    }
    public String getMonth() {
        return month;
    }
    public String getDay() {
        return day;
    }
    public Long getCreatedBy() {
        return createdBy;
    }
    public String getGroupId() { return groupId; }
}
