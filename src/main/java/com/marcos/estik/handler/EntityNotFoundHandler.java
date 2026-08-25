package com.marcos.estik.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.marcos.estik.domain.dto.ApiErrorDTO;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class EntityNotFoundHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> entityNotFoundHandler(
        EntityNotFoundException exception) {
            ApiErrorDTO error = new ApiErrorDTO(exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}