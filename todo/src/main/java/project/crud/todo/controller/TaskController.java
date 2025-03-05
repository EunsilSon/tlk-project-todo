package project.crud.todo.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.crud.todo.domain.dto.TaskDTO;
import project.crud.todo.domain.vo.TaskVO;
import project.crud.todo.global.response.ApiResponse;
import project.crud.todo.global.response.ResponseUtil;
import project.crud.todo.service.TaskServiceImpl;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TaskController {
    public final Logger log = LoggerFactory.getLogger(TaskController.class);
    public final TaskServiceImpl taskServiceImpl;

    @Autowired
    public TaskController(TaskServiceImpl taskServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
    }

    @PostMapping("/task")
    public ApiResponse<String> create(@Valid @RequestBody TaskVO taskVO) {
        try {
            taskServiceImpl.create(taskVO);
            return ResponseUtil.createSuccessResponse("Successes Create Task");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Create Task: " + e.getMessage());
        }
    }

    @PutMapping("/task")
    public ApiResponse<String> update(@Valid @RequestBody TaskVO taskVO) {
        try {
            taskServiceImpl.update(taskVO);
            return ResponseUtil.createSuccessResponse("Successes Update Task");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Update Task: " + e.getMessage());
        }
    }

    @DeleteMapping("/task/{id}")
    public ApiResponse<String> delete(@Valid @PathVariable Long id) {
        try {
            taskServiceImpl.delete(id);
            return ResponseUtil.createSuccessResponse("Successes Delete Task");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Delete Task: " + e.getMessage());
        }
    }

    @GetMapping("/task/{id}")
    public ApiResponse<TaskDTO> get(@PathVariable Long id) {
        try {
            return ResponseUtil.createSuccessResponse("Successes Get Task", taskServiceImpl.get(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Get Task: " + e.getMessage());
        }
    }

    @GetMapping("/tasks/{date}/{page}")
    public ApiResponse<List<TaskDTO>> getAllByDate(@PathVariable int page, @PathVariable String date) {
        List<TaskDTO> tasks = new ArrayList<>();
        try {
            if (date.length() == 7) { // yyyy-mm
                tasks = taskServiceImpl.getAllByYearAndMonth(page, date);
            }

            if (date.length() == 10) { // yyyy-mm-dd
                tasks = taskServiceImpl.getAllByDay(page, date);
            }
            return ResponseUtil.createSuccessResponse("Success Get Tasks", tasks);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Get Task: " + e.getMessage());
        }
    }

}