package com.marcos.estik.domain.dto.common;

import java.util.Map;

public record ValidationErrorDTO(
    Map<String, String> fields
) {
    
}