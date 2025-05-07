package com.spring.security.spring.boot.jwt.repository;

import com.spring.security.spring.boot.jwt.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

}
