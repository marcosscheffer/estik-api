package com.marcos.estik.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.marcos.estik.domain.dto.common.ApiErrorDTO;

import jakarta.persistence.EntityExistsException;

@RestControllerAdvice
public class EntityExistsHandler {
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ApiErrorDTO> entityExistsHandler(
        EntityExistsException exception) {
            ApiErrorDTO error = new ApiErrorDTO(exception.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}