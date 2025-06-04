package com.devtribe.global.error;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(
        IllegalArgumentException exception
    ) {
        log.debug("Invalid argument error: {}", exception.getMessage(), exception);
        ErrorResponse response = new ErrorResponse(exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleEnumMismatch(
        MethodArgumentTypeMismatchException exception
    ) {
        log.debug("Type mismatch error: {}", exception.getMessage(), exception);
        ErrorResponse response = new ErrorResponse("올바르지 않은 요청 값입니다.");
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
        HttpMessageNotReadableException exception
    ) {
        log.debug("Request parse error: {}", exception.getMessage(), exception);
        ErrorResponse response = new ErrorResponse("올바르지 않은 요청 값입니다.");
        return ResponseEntity.badRequest().body(response);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException exception) {
        log.error("DB 무결성 위반 에러: {}", exception.getMessage(), exception);
        ErrorResponse response = new ErrorResponse("요청 처리 중 오류가 발생했습니다.");
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException exception) {
        log.error("제약 조건 에러: {}", exception.getMessage(), exception);
        ErrorResponse response = new ErrorResponse("요청 처리 중 오류가 발생했습니다.");
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherExceptions(Exception exception) {
        log.error("제약 조건 에러: {}", exception.getMessage(), exception);
        ErrorResponse response = new ErrorResponse("서버 오류가 발생했습니다.");
        return ResponseEntity.internalServerError().body(response);
    }
}
