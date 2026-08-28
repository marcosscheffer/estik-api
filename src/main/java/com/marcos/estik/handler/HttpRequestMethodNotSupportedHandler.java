package com.marcos.estik.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.marcos.estik.domain.dto.common.ApiErrorDTO;

@RestControllerAdvice
public class HttpRequestMethodNotSupportedHandler {
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class) 
    public ResponseEntity<ApiErrorDTO> httpRequestMethodNotSupportHandler(
        HttpRequestMethodNotSupportedException exception) {
            ApiErrorDTO error = new ApiErrorDTO(exception.getMessage());
            return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(error);
        }
}