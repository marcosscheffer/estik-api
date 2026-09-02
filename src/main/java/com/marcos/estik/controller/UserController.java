package com.marcos.estik.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcos.estik.domain.dto.user.UserDTO;
import com.marcos.estik.domain.dto.user.UserSummaryDTO;
import com.marcos.estik.domain.dto.user.UserUpdateRoleDTO;
import com.marcos.estik.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserSummaryDTO>> getUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.getUsers(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<UserSummaryDTO> updateUserRole(
        @PathVariable Long id,
        @RequestBody UserUpdateRoleDTO dto
    ) {
        return ResponseEntity.ok(userService.updateUserRole(id, dto.role()));
    }
}
