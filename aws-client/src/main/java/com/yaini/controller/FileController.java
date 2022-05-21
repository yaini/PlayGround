package com.yaini.controller;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.yaini.controller.support.MultiPartResponseGenerator;
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
        return MultiPartResponseGenerator.of(fileService.download(fileName), fileName);
    }

    @PostMapping("/upload")
    public ObjectMetadata upload(@RequestParam("file") MultipartFile file) {
        return fileService.upload(file);
    }
}
