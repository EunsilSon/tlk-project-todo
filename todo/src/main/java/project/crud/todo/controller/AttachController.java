package project.crud.todo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.crud.todo.global.response.ApiResponse;
import project.crud.todo.global.response.ResponseUtil;
import project.crud.todo.service.AttachService;

@RestController
@RequestMapping("/attaches")
public class AttachController {
    private final AttachService attachService;

    public AttachController(AttachService attachService) {
        this.attachService = attachService;
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        if (attachService.delete(id)) {
            return ResponseUtil.createSuccessResponse("Successes Deleted file");
        }
        return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Deleted file");
    }

}
