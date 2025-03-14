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
import project.crud.todo.service.TaskService;

import java.util.List;

/**
 * CheckException, UncheckedException 알아보기
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {
    public final Logger log = LoggerFactory.getLogger(TaskController.class);
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ApiResponse<String> create(@Valid @RequestBody TaskVO taskVO) {
        taskService.create(taskVO);
        return ResponseUtil.createSuccessResponse("Successes Create Task");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        if (taskService.delete(id)) {
            return ResponseUtil.createSuccessResponse("Successes Delete Task");
        }
        return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Delete Task: Task Not Found");
    }

    // 이거 검색조건 따라 API 뜯은 거 같은 느낌인데, Condition같은 객체 만들어서 한번에 받을 수 있도록도 구성할 수 있음
    @GetMapping("/monthly")
    public ApiResponse<List<TaskDTO>> getAllByYearAndMonth(@RequestParam(defaultValue = "0") int page, @RequestParam int year, @RequestParam int month) {
        return ResponseUtil.createSuccessResponse("Success Get Tasks", taskService.getMonthlyTask(page, year, month));
    }

    @GetMapping("/daily")
    public ApiResponse<List<TaskDTO>> getAllByDate(@RequestParam(defaultValue = "0") int page, @RequestParam int year, @RequestParam int month, @RequestParam int day) {
        return ResponseUtil.createSuccessResponse("Success Get Tasks", taskService.getDailyTask(page, year, month, day));
    }

    // 이거 느낌이 한 페이지에서 count 갯수 찾아서 주는거같은데 맞나 ?
    // 그런거라면, paging API에서 한번에 찾아 주는게 나을 수 있음
    // front 입장에서 여러 번 쿼리 날리는건 UX 측면에서도 좋지 않기에, 한 쿼리에 다 담아서 줄 수 있으면 그게 베스트
    @GetMapping("/count")
    public ApiResponse<int[]> getCount(@RequestParam int year, @RequestParam int month) {
        return ResponseUtil.createSuccessResponse("Successes Get Task Counts", taskService.getTaskCount(year, month));
    }
}