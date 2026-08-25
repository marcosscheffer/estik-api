package com.marcos.estik.domain.dto;

import java.util.Map;

public record ValidationErrorDTO(
    Map<String, String> fields
) {
    
}