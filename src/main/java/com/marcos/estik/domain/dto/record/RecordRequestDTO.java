package com.marcos.estik.domain.dto.record;

import com.marcos.estik.domain.enums.RecordEnum;

import jakarta.validation.constraints.NotNull;

public record RecordRequestDTO(
    @NotNull
    Integer quantity,
    @NotNull
    RecordEnum direction,
    @NotNull
    Long itemId,
    @NotNull
    Long departamentId
) {
    
}
