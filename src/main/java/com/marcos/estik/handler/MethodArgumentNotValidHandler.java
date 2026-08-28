package com.marcos.estik.handler;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.marcos.estik.domain.dto.common.ValidationErrorDTO;

@RestControllerAdvice
public class MethodArgumentNotValidHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorDTO> handleValidation(
        MethodArgumentNotValidException exception) {
            Map<String, String> errors = new LinkedHashMap<>();

            exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(
                    error.getField(), 
                    error.getDefaultMessage()
                ));
            
            ValidationErrorDTO error = new ValidationErrorDTO(errors);
            
            return ResponseEntity.badRequest().body(error);
    }
}
