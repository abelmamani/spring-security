package com.spring.security.spring.boot.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.spring.security.spring.boot.jwt.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);
    Optional<UserEntity> findByUsername(String username);
}
