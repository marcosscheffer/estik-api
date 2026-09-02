package com.marcos.estik.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.marcos.estik.domain.dto.common.AuthDTO;
import com.marcos.estik.domain.dto.pc.PcSummaryUserDTO;
import com.marcos.estik.domain.dto.user.UserDTO;
import com.marcos.estik.domain.dto.user.UserSummaryDTO;
import com.marcos.estik.domain.entity.User;
import com.marcos.estik.domain.enums.RoleEnum;
import com.marcos.estik.repository.UserRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private UserSummaryDTO toDto(User user) {
        return new UserSummaryDTO(user.getId(), user.getUsername(), user.getRole());
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Assembler not found")
            );
    }

    public UserSummaryDTO createUser(AuthDTO dto) {
        if (userRepository.existsByUsername(dto.username())) {
            throw new EntityExistsException("username already exists");
        }

        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(RoleEnum.USER);
        user.setActive(false);
        userRepository.save(user);

        return toDto(user);
    }

    public UserSummaryDTO updateUserRole(Long id, RoleEnum role) {
        User user = getUserById(id);
        user.setRole(role);
        userRepository.save(user);
        return toDto(user);
    }

    public UserSummaryDTO updateUser(Long id, AuthDTO dto) {
        User user = getUserById(id);
        user.setUsername(dto.username());;
        user.setPassword(passwordEncoder.encode(dto.password()));
        return toDto(user);
    }

    public Page<UserSummaryDTO> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
            .map(user -> toDto(user));
    }

    public UserDTO getUser(Long id) {
        User user = getUserById(id);
        List<PcSummaryUserDTO> pcs = user.getPcsAssembled()
            .stream()
            .map(pc -> new PcSummaryUserDTO(pc.getId(), pc.getName()))
            .toList();

        return new UserDTO(
            user.getId(), 
            user.getUsername(), 
            user.getRole(),
            pcs
        );
    }

}
