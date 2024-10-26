package com.example.demo.exception;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

@Getter
public class WooCommerceException extends RuntimeException {
    private final JsonNode responseBody;

    public WooCommerceException(String message, JsonNode responseBody) {
        super(message);
        this.responseBody = responseBody;
    }
}
