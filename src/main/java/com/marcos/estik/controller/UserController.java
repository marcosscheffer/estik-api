package com.marcos.estik.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marcos.estik.domain.dto.user.UserDTO;
import com.marcos.estik.domain.dto.user.UserSummaryDTO;
import com.marcos.estik.domain.dto.user.UserUpdateActiveDTO;
import com.marcos.estik.domain.dto.user.UserUpdateRoleDTO;
import com.marcos.estik.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final UserService userService;

    @GetMapping
    @Operation(
        summary = "Retrieves system metrics",
        description = "<b>Restricted access:</b> Requires the <code>ROLE_SUPER</code> authority."
    )
    public ResponseEntity<Page<UserSummaryDTO>> getUsers(
        Pageable pageable,
        @RequestParam(name = "q", defaultValue = "") String q
    ) {
        return ResponseEntity.ok(userService.getUsers(q, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PutMapping("/{id}/role")
    @Operation(
        summary = "Retrieves system metrics",
        description = "<b>Restricted access:</b> Requires the <code>ROLE_SUPER</code> authority."
    )
    public ResponseEntity<UserSummaryDTO> updateUserRole(
        @PathVariable Long id,
        @RequestBody UserUpdateRoleDTO dto
    ) {
        return ResponseEntity.ok(userService.updateUserRole(id, dto.role()));
    }

    @PutMapping("/{id}/active")
    @Operation(
        summary = "Retrieves system metrics",
        description = "<b>Restricted access:</b> Requires the <code>ROLE_SUPER</code> authority."
    )
    public ResponseEntity<UserSummaryDTO> updateUserActiveStatus(
        @PathVariable Long id,
        @RequestBody UserUpdateActiveDTO dto
    ) {
        return ResponseEntity.ok(userService.updateUserActiveStatus(id, dto.active()));
    }
}
