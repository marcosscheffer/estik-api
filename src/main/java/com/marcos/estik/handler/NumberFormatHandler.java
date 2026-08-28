package com.marcos.estik.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.marcos.estik.domain.dto.common.ApiErrorDTO;


@RestControllerAdvice
public class NumberFormatHandler {
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ApiErrorDTO> noResourceFoundHandler(NumberFormatException exception) {
        ApiErrorDTO error = new ApiErrorDTO(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}