package com.marcos.estik.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.marcos.estik.domain.dto.common.ApiErrorDTO;

@RestControllerAdvice
public class DataIntegrityViolationHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorDTO> dataIntegrityViolationHandler(
        DataIntegrityViolationException exception) {
            ApiErrorDTO error = new ApiErrorDTO(exception.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }
}