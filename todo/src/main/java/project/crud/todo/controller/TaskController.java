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

@RestController
@RequestMapping("/tasks")
public class TaskController {
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
    /*
    * Condition 객체라고 검색하니까 동기화 관련 얘기들이 나옴
    * */
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

    /*
    한 달 기준으로 n일에 저장된 데이터 개수를 배열로 전달하는 것임
    페이지 로딩될 때 한 번 호출됨
    한 달 간 저장된 데이터를 페이징하는 api는 페이지가 로딩되고 n번 호출됨
    그래서 이걸 한 쿼리에 담는다면 페이징 할 때마다 같은 데이터가 중복으로 가게 됨
     */

    @GetMapping("/count")
    public ApiResponse<int[]> getCount(@RequestParam int year, @RequestParam int month) {
        return ResponseUtil.createSuccessResponse("Successes Get Task Counts", taskService.getTaskCount(year, month));
    }
}