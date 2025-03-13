package project.crud.todo.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.crud.todo.domain.dto.TaskDTO;
import project.crud.todo.domain.vo.TaskUpdateVO;
import project.crud.todo.domain.vo.TaskVO;
import project.crud.todo.global.response.ApiResponse;
import project.crud.todo.global.response.ResponseUtil;
import project.crud.todo.service.TaskServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    public final Logger log = LoggerFactory.getLogger(TaskController.class);
    public final TaskServiceImpl taskServiceImpl;

    @Autowired
    public TaskController(TaskServiceImpl taskServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
    }

    @PostMapping
    public ApiResponse<String> create(@Valid @RequestBody TaskVO taskVO) {
        if (taskServiceImpl.create(taskVO)) {
            return ResponseUtil.createSuccessResponse("Successes Create Task");
        }
        return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Create Task");
    }

    @PutMapping
    public ApiResponse<String> update(@Valid @RequestBody TaskUpdateVO taskUpdateVO) {
        if (taskServiceImpl.update(taskUpdateVO)) {
            return ResponseUtil.createSuccessResponse("Successes Update Task");
        }
        return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Update Task");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        if (taskServiceImpl.delete(id)) {
            return ResponseUtil.createSuccessResponse("Successes Delete Task");
        }
        return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Delete Task");
    }

    @GetMapping("/monthly")
    public ApiResponse<List<TaskDTO>> getAllByYearAndMonth(@RequestParam(defaultValue = "0") int page, @RequestParam int year, @RequestParam int month) {
        try {
            return ResponseUtil.createSuccessResponse("Success Get Tasks", taskServiceImpl.getAllByYearAndMonth(page, year, month));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Get Task: " + e.getMessage());
        }
    }

    @GetMapping("/daily")
    public ApiResponse<List<TaskDTO>> getAllByDate(@RequestParam(defaultValue = "0") int page, @RequestParam int year, @RequestParam int month, @RequestParam int day) {
        try {
            return ResponseUtil.createSuccessResponse("Success Get Tasks", taskServiceImpl.getAllByDay(page, year, month, day));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Get Task: " + e.getMessage());
        }
    }

    @GetMapping("/count")
    public ApiResponse<int[]> getCount(@RequestParam int year, @RequestParam int month) {
        try {
            return ResponseUtil.createSuccessResponse("Successes Get Task Counts", taskServiceImpl.getTaskCount(year, month));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Get Task Counts: " + e.getMessage());
        }
    }
}