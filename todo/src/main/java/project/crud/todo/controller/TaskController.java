package project.crud.todo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.crud.todo.domain.dto.TaskDTO;
import project.crud.todo.domain.vo.TaskVO;
import project.crud.todo.global.response.ApiResponse;
import project.crud.todo.global.response.ResponseUtil;
import project.crud.todo.service.TaskService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // TODO: File 객체 받기
    @PostMapping
    public ApiResponse<String> create(@Valid @RequestBody TaskVO taskVO, UUID groupId) {
        if (taskService.create(taskVO)) {
            return ResponseUtil.createSuccessResponse("Successes Create Task");
        }
        return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Create Task");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        if (taskService.delete(id)) {
            return ResponseUtil.createSuccessResponse("Successes Delete Task");
        }
        return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Delete Task: Task Not Found");
    }

    @GetMapping("/monthly")
    public ApiResponse<List<TaskDTO>> getAllByYearAndMonth(@RequestParam(defaultValue = "0") int page, @RequestParam int year, @RequestParam int month) {
        return ResponseUtil.createSuccessResponse("Success Get Tasks", taskService.getMonthlyTask(page, year, month));
    }

    @GetMapping("/daily")
    public ApiResponse<List<TaskDTO>> getAllByDate(@RequestParam(defaultValue = "0") int page, @RequestParam int year, @RequestParam int month, @RequestParam int day) {
        return ResponseUtil.createSuccessResponse("Success Get Tasks", taskService.getDailyTask(page, year, month, day));
    }

    @GetMapping("/count")
    public ApiResponse<int[]> getCount(@RequestParam int year, @RequestParam int month) {
        return ResponseUtil.createSuccessResponse("Successes Get Task Counts", taskService.getTaskCount(year, month));
    }
}