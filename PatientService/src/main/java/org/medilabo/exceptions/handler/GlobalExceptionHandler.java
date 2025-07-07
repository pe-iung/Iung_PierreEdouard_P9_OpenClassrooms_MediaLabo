
package org.medilabo.exceptions.handler;

import lombok.extern.slf4j.Slf4j;
import org.medilabo.exceptions.PatientNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<?> handlePatientNotFound(PatientNotFoundException ex, WebRequest request) {
        log.warn("Patient not found: {}", ex.getMessage());
        ProblemDetail body = createProblemDetail(
                ex,
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                null,
                null,
                request
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(body);
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex, WebRequest request) {
        log.error("Unexpected error occurred", ex);
        ProblemDetail body = createProblemDetail(
                ex,
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred",
                null,
                null,
                request
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }

    protected ProblemDetail createProblemDetail(Exception ex, HttpStatus status,
                                                String defaultDetail, String messageCode, Object[] args, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, defaultDetail);
        problemDetail.setProperty("timestamp", OffsetDateTime.now());
        if (messageCode != null) {
            problemDetail.setProperty("messageCode", messageCode);
        }
        if (args != null) {
            problemDetail.setProperty("details", args);
        }
        return problemDetail;
    }
}
