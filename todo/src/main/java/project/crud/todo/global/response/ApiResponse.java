package project.crud.todo.global.response;

import org.springframework.http.HttpStatusCode;

public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;

    public ApiResponse(HttpStatusCode status, String message) {
        this.status = status.value();
        this.message = message;
        this.data = (T) "empty";
    }

    public ApiResponse(HttpStatusCode status, String message, T data) {
        this.status = status.value();
        this.message = message;
        this.data = (data == null) ? (T) "empty" : data;
    }

    public int getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
    public T getData() {
        return data;
    }

}