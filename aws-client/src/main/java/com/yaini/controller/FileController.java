package com.yaini.controller;

import com.yaini.controller.response.ApiResponse;
import com.yaini.controller.support.ApiResponseGenerator;
import com.yaini.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
@RestController
public class FileController {

  private final FileService fileService;

  @GetMapping("/download")
  public ResponseEntity<byte[]> download(final @RequestParam String fileName) {
    return ApiResponseGenerator.of(fileService.download(fileName), fileName);
  }

  @PostMapping("/upload")
  public ApiResponse<String> upload(@RequestParam("file") MultipartFile file) {
    return ApiResponseGenerator.of(fileService.upload(file));
  }
}
