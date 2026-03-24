package dev.anthonyadcs.beverage_production_system.dto.response;

import org.springframework.http.HttpStatusCode;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
        int statusCode,
        String errorName,
        String message,
        Instant timestamp,
        String path,
        List<FieldErrorResponse> errors
) {}
