package com.isi.ExamenMricoService.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Global exception handler that converts common exceptions into consistent JSON error responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400 - Invalid JSON or incompatible payload
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse body = ErrorResponse.of(status, "Malformed JSON request", requestPath(request));
        body.setDetails(mapOf("cause", rootMessage(ex)));
        return ResponseEntity.status(status).body(body);
    }

    // 400 - Bean validation on @RequestBody payloads
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Map<String, Object> details = new LinkedHashMap<>();

        // Field errors
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fe.getField(), fe.getDefaultMessage());
        }
        if (!fieldErrors.isEmpty()) {
            details.put("fieldErrors", fieldErrors);
        }

        // Global errors (object level)
        if (!ex.getBindingResult().getGlobalErrors().isEmpty()) {
            details.put("globalErrors", ex.getBindingResult().getGlobalErrors().stream()
                .map(err -> err.getObjectName() + ": " + err.getDefaultMessage())
                .toList());
        }

        ErrorResponse body = ErrorResponse.of(status, "Validation failed", requestPath(request));
        body.setDetails(details);
        return ResponseEntity.status(status).body(body);
    }

    // 400 - Bean validation on @RequestParam, @PathVariable, etc.
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Map<String, String> violations = new LinkedHashMap<>();
        for (ConstraintViolation<?> v : ex.getConstraintViolations()) {
            violations.put(String.valueOf(v.getPropertyPath()), v.getMessage());
        }
        ErrorResponse body = ErrorResponse.of(status, "Constraint violations", requestPath(request));
        body.setDetails(mapOf("violations", violations));
        return ResponseEntity.status(status).body(body);
    }

    // 404 - JPA not-found
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse body = ErrorResponse.of(status, msgOrDefault(ex, "Resource not found"), requestPath(request));
        return ResponseEntity.status(status).body(body);
    }

    // Data constraints (unique keys, FK violations, etc.)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        ErrorResponse body = ErrorResponse.of(status, "Database constraint violation", requestPath(request));
        body.setDetails(mapOf("cause", rootMessage(ex)));
        return ResponseEntity.status(status).body(body);
    }

    // Propagate ResponseStatusException nicely
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatus(ResponseStatusException ex, WebRequest request) {
        HttpStatus status = HttpStatus.resolve(ex.getStatusCode().value());
        if (status == null) status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = ex.getReason() != null ? ex.getReason() : status.getReasonPhrase();
        ErrorResponse body = ErrorResponse.of(status, message, requestPath(request));
        return ResponseEntity.status(status).body(body);
    }

    // Catch-all
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse body = ErrorResponse.of(status, "Unexpected error", requestPath(request));
        body.setDetails(mapOf("cause", rootMessage(ex)));
        return ResponseEntity.status(status).body(body);
    }

    // ---------- Helpers ----------

    private static String requestPath(WebRequest request) {
        if (request instanceof ServletWebRequest swr) {
            HttpServletRequest req = swr.getRequest();
            String uri = req.getRequestURI();
            String query = req.getQueryString();
            return query == null ? uri : (uri + "?" + query);
        }
        return null;
    }

    private static String rootMessage(Throwable ex) {
        Throwable root = ex;
        while (root.getCause() != null && root.getCause() != root) {
            root = root.getCause();
        }
        return root.getMessage();
    }

    private static String msgOrDefault(Throwable ex, String fallback) {
        return ex.getMessage() == null || ex.getMessage().isBlank() ? fallback : ex.getMessage();
    }

    private static Map<String, Object> mapOf(String key, Object val) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(key, val);
        return map;
    }

    // ---------- DTO ----------

    /**
     * Simple error payload returned by the API.
     */
    public static class ErrorResponse {
        private OffsetDateTime timestamp;
        private int status;
        private String error;
        private String message;
        private String path;
        private Map<String, Object> details;

        public static ErrorResponse of(HttpStatus status, String message, String path) {
            ErrorResponse r = new ErrorResponse();
            r.timestamp = OffsetDateTime.now();
            r.status = status.value();
            r.error = status.getReasonPhrase();
            r.message = message;
            r.path = path;
            r.details = new LinkedHashMap<>();
            return r;
        }

        // Getters and setters for Jackson

        public OffsetDateTime getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(OffsetDateTime timestamp) {
            this.timestamp = timestamp;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Map<String, Object> getDetails() {
            return details;
        }

        public void setDetails(Map<String, Object> details) {
            this.details = details;
        }
    }
}
