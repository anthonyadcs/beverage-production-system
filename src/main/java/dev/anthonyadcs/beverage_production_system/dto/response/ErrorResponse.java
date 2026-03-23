package dev.anthonyadcs.beverage_production_system.dto.response;

import org.springframework.http.HttpStatusCode;

import java.time.Instant;

public record _ErrorResponse (
        HttpStatusCode statusCode,
        String error,
        String message,
        Instant timestamp,
        String path
) {}
