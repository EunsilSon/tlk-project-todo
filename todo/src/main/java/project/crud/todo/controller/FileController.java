package project.crud.todo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.crud.todo.global.response.ApiResponse;
import project.crud.todo.global.response.ResponseUtil;
import project.crud.todo.service.FileService;

@RestController
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @DeleteMapping("/{groupId}")
    public ApiResponse<String> delete(@PathVariable String groupId) {
        if (fileService.delete(groupId)) {
            return ResponseUtil.createSuccessResponse("Successes Deleted file");
        } else {
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Deleted file");
        }
    }

    @GetMapping("/{groupId}")
    public ApiResponse<String> getCount(@PathVariable String groupId) {
        return ResponseUtil.createSuccessResponse("Total files found: " + fileService.getCount((groupId)));
    }
}
