package com.marcos.estik.domain.dto.user;

import java.util.List;

import com.marcos.estik.domain.dto.pc.PcSummaryUserDTO;

public record UserDTO(
    Long id,
    String username,
    List<PcSummaryUserDTO> pcs
) {
    
}
