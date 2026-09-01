package com.marcos.estik.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.marcos.estik.domain.dto.common.AuthResponseDTO;
import com.marcos.estik.domain.dto.user.UserSummaryDTO;
import com.marcos.estik.domain.dto.common.AuthDTO;
import com.marcos.estik.domain.entity.User;
import com.marcos.estik.service.TokenService;
import com.marcos.estik.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController()
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthDTO dto) {
        
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        Authentication auth = this.authenticationManager.authenticate(user);
        User userLogged = (User) auth.getPrincipal();

        AuthResponseDTO response = new AuthResponseDTO(this.tokenService.generateToken(userLogged.getUsername(), userLogged.getId()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<UserSummaryDTO> register(
        @RequestBody @Valid AuthDTO dto,
        UriComponentsBuilder uriBuilder
    ) {
        UserSummaryDTO user = userService.createUser(dto);

        URI uri = uriBuilder
            .path("/users/{id}")
            .buildAndExpand(user.id())
            .toUri();

        return ResponseEntity.created(uri).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserSummaryDTO> updateUser(
        @PathVariable Long id,
        @RequestBody @Valid AuthDTO dto
    ) {
        UserSummaryDTO user = userService.updateUser(id, dto);

        return ResponseEntity.ok(user);
    }
}
