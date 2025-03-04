package project.crud.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.crud.todo.response.ApiResponse;
import project.crud.todo.response.ResponseUtil;
import project.crud.todo.service.TaskService;

@RestController
public class TaskController {
    public final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/test")
    public ApiResponse<String> test() {
        if (taskService.test()) {
            return ResponseUtil.createSuccessResponse();
        }
        return  ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Not Found");
    }

}