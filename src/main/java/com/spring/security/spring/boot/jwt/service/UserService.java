package com.spring.security.spring.boot.jwt.service;

import com.spring.security.spring.boot.jwt.controller.dto.CreateUserDTO;
import com.spring.security.spring.boot.jwt.controller.dto.UpdateUserDTO;
import com.spring.security.spring.boot.jwt.persistence.entity.ERole;
import com.spring.security.spring.boot.jwt.persistence.entity.RoleEntity;
import com.spring.security.spring.boot.jwt.persistence.entity.UserEntity;
import com.spring.security.spring.boot.jwt.exception.RoleException;
import com.spring.security.spring.boot.jwt.exception.UserException;
import com.spring.security.spring.boot.jwt.persistence.repository.RoleRepository;
import com.spring.security.spring.boot.jwt.persistence.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void save(CreateUserDTO createUserDTO) {
        if (userRepository.existsByUsername(createUserDTO.getUsername())) {
            throw new UserException("El usuario " + createUserDTO.getUsername() + " ya existe");
        }

        Set<RoleEntity> roles = getRoles(createUserDTO.getRoles());

        UserEntity user = UserEntity.builder()
                .username(createUserDTO.getUsername())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .roles(roles)
                .build();

        userRepository.save(user);
    }

    @Transactional
    public void update(Long userId, UpdateUserDTO updateUserDTO) {
        UserEntity existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("Usuario no encontrado con ID: " + userId));

        if (userRepository.existsByUsername(updateUserDTO.getUsername()) &&
                !existingUser.getUsername().equals(updateUserDTO.getUsername())) {
            throw new UserException("El nombre de usuario ya está en uso");
        }

        Set<RoleEntity> roles = getRoles(updateUserDTO.getRoles());

        existingUser.setUsername(updateUserDTO.getUsername());
        existingUser.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
        existingUser.setRoles(roles);

        userRepository.save(existingUser);
    }

    @Transactional
    public void deleteById(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserException("Usuario no encontrado con ID: " + userId);
        }
        userRepository.deleteById(userId);
    }

    private Set<RoleEntity> getRoles(List<String> roleNames) {
        return roleNames.stream()
                .map(roleName -> {
                    try {
                        ERole roleEnum = ERole.valueOf(roleName);
                        Optional<RoleEntity> find = roleRepository.findByName(roleEnum);
                        return find.orElseGet(() -> RoleEntity.builder().id(null).name(roleEnum).build());
                    } catch (IllegalArgumentException e) {
                        String rolesValidos = Arrays.stream(ERole.values())
                                .map(Enum::name)
                                .collect(Collectors.joining(", "));
                        throw new RoleException("Rol '" + roleName + "' no válido. Roles válidos: " + rolesValidos);   }
                })
                .collect(Collectors.toSet());
    }
}