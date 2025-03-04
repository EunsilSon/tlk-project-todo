package project.crud.todo.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class ResponseUtil {

    public static ApiResponse<String> createSuccessResponse(String message) {
        return new ApiResponse<>(HttpStatus.OK, message);
    }

    public static <T> ApiResponse<T> createSuccessResponse(String message, T data) {
        return new ApiResponse<>(HttpStatus.OK, message, data);
    }

    public static <T> ApiResponse<T> createErrorResponse(HttpStatusCode status, String message) {
        return new ApiResponse<>(status, message);
    }
}