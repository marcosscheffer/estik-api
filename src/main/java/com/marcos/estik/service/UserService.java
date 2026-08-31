package com.marcos.estik.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.marcos.estik.domain.dto.common.AuthDTO;
import com.marcos.estik.domain.dto.common.UserSummaryDTO;
import com.marcos.estik.domain.entity.User;
import com.marcos.estik.repository.UserRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private UserSummaryDTO toDto(User user) {
        return new UserSummaryDTO(user.getId(), user.getUsername());
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Assembler not found")
            );
    }

    public UserSummaryDTO createUser(AuthDTO dto) {
        if (userRepository.ExistsByUsername(dto.username())) {
            throw new EntityExistsException("username already exists");
        }

        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        userRepository.save(user);

        return toDto(user);
    }

    public UserSummaryDTO updateUser(Long id, AuthDTO dto) {
        User user = getUserById(id);
        user.setUsername(dto.username());;
        user.setPassword(passwordEncoder.encode(dto.password()));
        return toDto(user);
    }

}
