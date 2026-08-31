package com.marcos.estik.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcos.estik.domain.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
    
}
