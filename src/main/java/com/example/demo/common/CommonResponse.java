package com.example.demo.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonResponse<T> {
    private String status;
    private Long recordId;
    private T data;
    private ErrorDetails<?> error;

    @Data
    @Builder
    public static class ErrorDetails<T> {
        private T message;
    }
}
