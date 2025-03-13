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
import project.crud.todo.service.TaskService;

import java.util.List;

/**
 * Slf4j 알아보기
 * JavaDoc 알아보기
 * Lombok 알아보기
 * CheckException, UncheckedException 알아보기
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {
    public final Logger log = LoggerFactory.getLogger(TaskController.class);
    private final TaskService taskService; //바깥에서 쓸 게 아니라면 private 생성자, Impl 받지 말고 interface 받기

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping // 이거 Convention 알아보기. 우리는 안에 URL을 꼭 명시하는 편
    public ApiResponse<String> create(@Valid @RequestBody TaskVO taskVO) {
        try { // 모든곳에 try catch를 잡으니, 코드 중복이 많은 것 같아보임. GlobalExceptionHandler에서 처리 고민 필요.
            taskService.create(taskVO);
            return ResponseUtil.createSuccessResponse("Successes Create Task");
        } catch (Exception e) {
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Create Task");
        }
    }

    @PutMapping
    public ApiResponse<String> update(@Valid @RequestBody TaskUpdateVO taskUpdateVO) {
        try {
            taskService.update(taskUpdateVO);
            return ResponseUtil.createSuccessResponse("Successes Update Task");
        } catch (Exception e) {
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Update Task");
        }
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
        try {
            return ResponseUtil.createSuccessResponse("Success Get Tasks", taskService.getMonthlyTask(page, year, month));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Get Task: " + e.getMessage());
        }
    }

    @GetMapping("/daily")
    public ApiResponse<List<TaskDTO>> getAllByDate(@RequestParam(defaultValue = "0") int page, @RequestParam int year, @RequestParam int month, @RequestParam int day) {
        try {
            return ResponseUtil.createSuccessResponse("Success Get Tasks", taskService.getDailyTask(page, year, month, day));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Get Task: " + e.getMessage());
        }
    }

    // 이거 느낌이 한 페이지에서 count 갯수 찾아서 주는거같은데 맞나 ?
    // 그런거라면, paging API에서 한번에 찾아 주는게 나을 수 있음
    // front 입장에서 여러 번 쿼리 날리는건 UX 측면에서도 좋지 않기에, 한 쿼리에 다 담아서 줄 수 있으면 그게 베스트
    @GetMapping("/count")
    public ApiResponse<int[]> getCount(@RequestParam int year, @RequestParam int month) {
        try {
            return ResponseUtil.createSuccessResponse("Successes Get Task Counts", taskService.getTaskCount(year, month));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Get Task Counts: " + e.getMessage());
        }
    }
}