package com.example.clone_project.exception;

import com.example.clone_project.dto.response.ResponseDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdviser {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseDto<?> illegalArgumentHandler(IllegalArgumentException e) {
        return ResponseDto.fail("ILLEGAL_ARGUMENT", e.getMessage());
    }
}
