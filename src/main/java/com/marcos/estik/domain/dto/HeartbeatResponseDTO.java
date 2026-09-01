package com.marcos.estik.domain.dto;

import com.marcos.estik.domain.enums.StatusEnum;

public record HeartbeatResponseDTO (
    String name,
    String ip,
    StatusEnum status
) {

}
