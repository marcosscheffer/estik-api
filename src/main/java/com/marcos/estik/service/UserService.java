package com.marcos.estik.service;

import org.springframework.stereotype.Service;

import com.marcos.estik.domain.entity.User;
import com.marcos.estik.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Assembler not found")
            );
    }
}
