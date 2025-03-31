package project.crud.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.crud.todo.domain.dto.TaskDTO;
import project.crud.todo.domain.vo.TaskVO;
import project.crud.todo.global.response.ApiResponse;
import project.crud.todo.global.response.ResponseUtil;
import project.crud.todo.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final int DEFAULT_TASK_SIZE = 20;
    private final String DEFAULT_TASK_SORT_BY = "scheduledDate";

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ApiResponse<String> create(@RequestParam(value = "attaches", required = false) List<MultipartFile> files,
                                      @ModelAttribute TaskVO taskVO) {
        if (taskService.create(files, taskVO)) {
            return ResponseUtil.createSuccessResponse("Success Create Task");
        }
        return ResponseUtil.createErrorResponse(HttpStatus.BAD_REQUEST, "Failed Create Task");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        if (taskService.delete(id)) {
            return ResponseUtil.createSuccessResponse("Successes Delete Task");
        }
        return ResponseUtil.createErrorResponse(HttpStatus.BAD_REQUEST, "Failed Delete Task");
    }

    @GetMapping("/monthly")
    public ApiResponse<List<TaskDTO>> getAllByYearAndMonth(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam int year,
                                                           @RequestParam int month,
                                                           @PageableDefault(size = DEFAULT_TASK_SIZE, sort = DEFAULT_TASK_SORT_BY) Pageable pageable) {
        return ResponseUtil.createSuccessResponse("Success Get Tasks", taskService.getMonthlyTask(page, year, month, pageable));
    }

    @GetMapping("/daily")
    public ApiResponse<List<TaskDTO>> getAllByDate(@RequestParam(defaultValue = "0") int page, @RequestParam int year,
                                                   @RequestParam int month,
                                                   @RequestParam int day,
                                                   @PageableDefault(size = DEFAULT_TASK_SIZE, sort = DEFAULT_TASK_SORT_BY) Pageable pageable) {
        return ResponseUtil.createSuccessResponse("Success Get Tasks", taskService.getDailyTask(page, year, month, day, pageable));
    }

    @GetMapping("/count")
    public ApiResponse<int[]> getCount(@RequestParam int year,
                                       @RequestParam int month) {
        return ResponseUtil.createSuccessResponse("Successes Get Task Counts", taskService.getTaskCount(year, month));
    }
}