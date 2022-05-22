package com.yaini.controller.support;

import com.yaini.controller.response.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ApiResponseGenerator {

    public static <T> ApiResponse<T> of(final T data) {
        return ApiResponse.<T>builder().data(data).build();
    }

    public static ResponseEntity<byte[]> of(final byte[] file, final String filename) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.length)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(file);
    }
}
