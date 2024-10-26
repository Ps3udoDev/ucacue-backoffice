package com.example.demo.configuration;

import com.example.demo.common.CommonResponse;
import com.example.demo.exception.WooCommerceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder message = new StringBuilder("Validation failed for: ");
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            message.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; ");
        });

        CommonResponse<CommonResponse.ErrorDetails> response = CommonResponse.<CommonResponse.ErrorDetails>builder()
                .status("ERROR")
                .error(CommonResponse.ErrorDetails.builder()
                        .message(message.toString())
                        .build())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CommonResponse<?>> handleConstraintViolationException(ConstraintViolationException ex) {
        StringBuilder message = new StringBuilder("Validation failed for: ");
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            message.append(violation.getPropertyPath()).append(" - ").append(violation.getMessage()).append("; ");
        }

        CommonResponse<CommonResponse.ErrorDetails> response = CommonResponse.<CommonResponse.ErrorDetails>builder()
                .status("ERROR")
                .error(CommonResponse.ErrorDetails.builder()
                        .message(message.toString())
                        .build())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CommonResponse<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        CommonResponse<CommonResponse.ErrorDetails> response = CommonResponse.<CommonResponse.ErrorDetails>builder()
                .status("ERROR")
                .error(CommonResponse.ErrorDetails.builder()
                        .message("Required request body is missing")
                        .build())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CommonResponse<?>> handleRuntimeException(RuntimeException ex) {
        CommonResponse<CommonResponse.ErrorDetails> response = CommonResponse.<CommonResponse.ErrorDetails>builder()
                .status("ERROR")
                .error(CommonResponse.ErrorDetails.builder()
                        .message("An error occurred: " + ex.getMessage())
                        .build())
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(WooCommerceException.class)
    public ResponseEntity<CommonResponse<?>> handleWooCommerceException(WooCommerceException ex) {
        JsonNode responseBody = ex.getResponseBody();

        if (responseBody != null) {
            String errorCode = responseBody.has("code") ? responseBody.get("code").asText() : "unknown_code";
            String errorMessage = responseBody.has("message") ? responseBody.get("message").asText() : "unknown_message";
            JsonNode errorData = responseBody.has("data") ? responseBody.get("data") : null;

            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("code", errorCode);
            errorDetails.put("message", errorMessage);

            if (errorData != null && errorData.has("status")) {
                errorDetails.put("statusCode", errorData.get("status").asInt());
            }
            if (errorData != null && errorData.has("resource_id")) {
                errorDetails.put("resourceId", errorData.get("resource_id").asInt());
            }

            CommonResponse<Map<String, Object>> response = CommonResponse.<Map<String, Object>>builder()
                    .status("ERROR")
                    .error(CommonResponse.ErrorDetails.<Map<String, Object>>builder()
                            .message(errorDetails)
                            .build())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            CommonResponse<String> response = CommonResponse.<String>builder()
                    .status("ERROR")
                    .error(CommonResponse.ErrorDetails.<String>builder()
                            .message("Unknown error occurred, no response body")
                            .build())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
