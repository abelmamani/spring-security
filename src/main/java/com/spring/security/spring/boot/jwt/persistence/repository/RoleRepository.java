package com.spring.security.spring.boot.jwt.persistence.repository;

import com.spring.security.spring.boot.jwt.persistence.entity.ERole;
import com.spring.security.spring.boot.jwt.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(ERole name);
}
