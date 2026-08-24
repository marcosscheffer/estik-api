package com.marcos.estik.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcos.estik.domain.dto.AuthResponseDTO;
import com.marcos.estik.domain.dto.LoginDTO;
import com.marcos.estik.domain.entity.User;
import com.marcos.estik.service.TokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController()
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginDTO dto) {
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        Authentication auth = this.authenticationManager.authenticate(user);
        User userLogged = (User) auth.getPrincipal();

        AuthResponseDTO response = new AuthResponseDTO(this.tokenService.generateToken(userLogged.getUsername()));
        return ResponseEntity.ok(response);
    }
}
