package project.crud.todo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.crud.todo.domain.dto.TaskDTO;
import project.crud.todo.domain.vo.TaskVO;
import project.crud.todo.response.ApiResponse;
import project.crud.todo.response.ResponseUtil;
import project.crud.todo.service.TaskService;


@Slf4j
@RestController
public class TaskController {
    public final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/task")
    public ApiResponse<String> create(@RequestBody TaskVO taskVO) {
        try {
            taskService.create(taskVO);
            return ResponseUtil.createSuccessResponse("Successes Create Task");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Create Task: " + e.getMessage());
        }
    }

    @PutMapping("/task")
    public ApiResponse<String> update(@RequestBody TaskVO taskVO) {
        try {
            taskService.update(taskVO);
            return ResponseUtil.createSuccessResponse("Successes Update Task");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Update Task: " + e.getMessage());
        }
    }

    @DeleteMapping("/task/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        try {
            taskService.delete(id);
            return ResponseUtil.createSuccessResponse("Successes Delete Task");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Delete Task: " + e.getMessage());
        }
    }

    @GetMapping("/task/{id}")
    public ApiResponse<TaskDTO> get(@PathVariable Long id) {
        try {
            return ResponseUtil.createSuccessResponse("Successes Get Task", taskService.get(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Get Task: " + e.getMessage());
        }
    }

}