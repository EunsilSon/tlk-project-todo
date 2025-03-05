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
    public ApiResponse<String> delete(@PathVariable Long id) {
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

    @GetMapping("/tasks/monthly")
    public ApiResponse<List<TaskDTO>> getAllByYearAndMonth(@RequestParam(defaultValue = "0") int page, @RequestParam int year, @RequestParam int month) {
        try {
            return ResponseUtil.createSuccessResponse("Success Get Tasks", taskServiceImpl.getAllByYearAndMonth(page, year, month));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Get Task: " + e.getMessage());
        }
    }

    @GetMapping("/tasks/daily")
    public ApiResponse<List<TaskDTO>> getAllByDate(@RequestParam(defaultValue = "0") int page, @RequestParam int year, @RequestParam int month, @RequestParam int day) {
        try {
            return ResponseUtil.createSuccessResponse("Success Get Tasks", taskServiceImpl.getAllByDay(page, year, month, day));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Get Task: " + e.getMessage());
        }
    }

    @GetMapping("/task/count")
    public ApiResponse<int[]> getCount(@RequestParam int year, @RequestParam int month) {
        try {
            return ResponseUtil.createSuccessResponse("Successes Get Task Counts", taskServiceImpl.getTaskCountByYearAndMonth(year, month));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Get Task Counts: " + e.getMessage());
        }
    }
}