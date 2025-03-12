package com.lvrgese.movie_api.controller;

import com.lvrgese.movie_api.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file")
@Tag(name = "File handling endpoints",description = "Endpoints for file related operations")
public class FileController {

    private final FileService fileService;

    @Value("${poster.upload-dir}")
    private String uploadDir;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @Operation(summary = "Upload a movie poster")
    @ApiResponse(responseCode ="200",description = "Successfully uploaded the poster")
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFIleHandler(@RequestPart MultipartFile file) throws IOException {
        String uploadedFileName =  fileService.uploadFile(uploadDir,file);
        return ResponseEntity.ok("File uploaded: "+uploadedFileName);
    }

    @Operation(summary = "View a movie poster by filename")
    @ApiResponse(responseCode ="200",description = "Successfully retrieved the poster")
    @GetMapping("/{fileName}")
    public void serveFileHandler(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        InputStream resource = fileService.getResourceFile(uploadDir,fileName);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
