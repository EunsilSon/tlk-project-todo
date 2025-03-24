package project.crud.todo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.crud.todo.global.response.ApiResponse;
import project.crud.todo.global.response.ResponseUtil;
import project.crud.todo.service.ImageService;

@RestController
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @DeleteMapping("/{groupId}")
    public ApiResponse<String> delete(@PathVariable String groupId) {
        if (imageService.delete(groupId)) {
            return ResponseUtil.createSuccessResponse("Successes Deleted file");
        } else {
            return ResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "Failed Deleted file");
        }
    }

    /*@GetMapping("/{groupId}")
    public ApiResponse<String> getCount(@PathVariable String groupId) {
        return ResponseUtil.createSuccessResponse("Total files found: " + imageService.getCount((groupId)));
    }*/
}
