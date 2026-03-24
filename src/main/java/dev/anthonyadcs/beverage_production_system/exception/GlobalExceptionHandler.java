package dev.anthonyadcs.beverage_production_system.exception;

import dev.anthonyadcs.beverage_production_system.dto.response.ErrorResponse;
import dev.anthonyadcs.beverage_production_system.dto.response.FieldErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exception,
            HttpServletRequest request
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase().toUpperCase(),
                "Corpo da requisição inválido ou com campos incorretos.",
                Instant.now(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(errorResponse.statusCode()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        List<FieldErrorResponse> fieldErrorResponses = exception
                .getBindingResult()
                .getFieldErrors()
                .stream().map(error -> new FieldErrorResponse(error.getField(), error.getDefaultMessage()))
                .toList();

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase().toUpperCase(),
                "Um ou mais campos da requisição inválidos.",
                Instant.now(),
                request.getRequestURI(),
                fieldErrorResponses
        );

        return ResponseEntity.status(errorResponse.statusCode()).body(errorResponse);
    }

    @ExceptionHandler(InvalidEntityCodeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidEntityCodeException(
            InvalidEntityCodeException exception,
            HttpServletRequest request
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase().toUpperCase(),
                exception.getMessage(),
                Instant.now(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(errorResponse.statusCode()).body(errorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(
            EntityNotFoundException exception,
            HttpServletRequest request
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase().toUpperCase(),
                exception.getMessage(),
                Instant.now(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(errorResponse.statusCode()).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException exception,
            HttpServletRequest request
    ) {
        String message = null;

        if(exception.getMessage().contains("UUID")){
            message = "Código de identificação fora do padrão esperado.";
        }

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase().toUpperCase(),
                Optional.ofNullable(message).orElse("Um ou mais parâmetros informados são inválidos ou estão em um formato incorreto."),
                Instant.now(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(errorResponse.statusCode()).body(errorResponse);
    }

    @ExceptionHandler(InvalidEntityStateException.class)
    public ResponseEntity<ErrorResponse> handleInvalidEntityStateException(
            InvalidEntityStateException exception,
            HttpServletRequest request
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_CONTENT.value(),
                HttpStatus.UNPROCESSABLE_CONTENT.getReasonPhrase().toUpperCase(),
                exception.getMessage(),
                Instant.now(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(errorResponse.statusCode()).body(errorResponse);
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<ErrorResponse> handleInvalidArgumentException(
            InvalidArgumentException exception,
            HttpServletRequest request
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_CONTENT.value(),
                HttpStatus.UNPROCESSABLE_CONTENT.getReasonPhrase().toUpperCase(),
                exception.getMessage(),
                Instant.now(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(errorResponse.statusCode()).body(errorResponse);
    }
}
