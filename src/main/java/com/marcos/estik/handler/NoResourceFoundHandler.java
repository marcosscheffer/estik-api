package com.marcos.estik.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.marcos.estik.domain.dto.common.ApiErrorDTO;


@RestControllerAdvice
public class NoResourceFoundHandler {
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiErrorDTO> noResourceFoundHandler(NoResourceFoundException exception) {
        ApiErrorDTO error = new ApiErrorDTO(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}