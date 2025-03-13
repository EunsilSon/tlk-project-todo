package project.crud.todo.domain.vo;

import jakarta.validation.constraints.Size;

/**
 * Validation을 한건 좋은데, 왜 하나만 ?
 */
public class TaskVO {
    @Size(min = 1, max = 100, message = "입력 길이는 최소 1자, 최대 100자입니다.")
    private String content;
    private Integer year;
    private Integer month;
    private Integer day;

    public String getContent() {
        return content;
    }
    public Integer getYear() {
        return year;
    }
    public Integer getMonth() {
        return month;
    }
    public Integer getDay() {
        return day;
    }
}
