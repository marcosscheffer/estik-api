package com.marcos.estik.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.marcos.estik.domain.dto.common.ApiErrorDTO;

@RestControllerAdvice
public class BadCredentialsHandler {
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorDTO> disabledHandler(
        BadCredentialsException exception) {
            ApiErrorDTO error = new ApiErrorDTO(exception.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }
}