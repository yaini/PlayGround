package com.yaini.controller.response;

import lombok.Builder;

@Builder
public class ApiResponse<T> {

    private final String message;
    private final T data;

    public ApiResponse(final String message, final T data) {
        this.message = message;
        this.data = data;
    }
}
