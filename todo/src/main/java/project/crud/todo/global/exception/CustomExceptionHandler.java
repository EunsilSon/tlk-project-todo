package project.crud.todo.global.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.crud.todo.global.response.ApiResponse;
import project.crud.todo.global.response.ResponseUtil;

@RestControllerAdvice
public class CustomExceptionHandler {
    Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(logMessageTitle(), e.getMessage());
        return ResponseUtil.createErrorResponse(HttpStatus.BAD_REQUEST, e.getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ApiResponse<String> handleException(Exception e) {
        log.error(logMessageTitle(), e.getMessage());
        return ResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    private String logMessageTitle() {
        return "[Fail] {}";
    }
}
