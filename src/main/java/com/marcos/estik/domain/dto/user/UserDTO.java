package com.marcos.estik.domain.dto.user;

import java.util.List;

import com.marcos.estik.domain.dto.pc.PcSummaryUserDTO;
import com.marcos.estik.domain.enums.RoleEnum;

public record UserDTO(
    Long id,
    String username,
    RoleEnum role,
    List<PcSummaryUserDTO> pcs
) {
    
}
