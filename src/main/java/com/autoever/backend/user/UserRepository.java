package com.autoever.backend.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByAccount(String account);
    boolean existsByRegNo(String regNo);
    Optional<User> findByAccount(String account);
}
