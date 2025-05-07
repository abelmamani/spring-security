package com.spring.security.spring.boot.jwt.service;

import com.spring.security.spring.boot.jwt.dto.CreateUserDTO;
import com.spring.security.spring.boot.jwt.dto.UpdateUserDTO;
import com.spring.security.spring.boot.jwt.entity.ERole;
import com.spring.security.spring.boot.jwt.entity.RoleEntity;
import com.spring.security.spring.boot.jwt.entity.UserEntity;
import com.spring.security.spring.boot.jwt.exception.RoleException;
import com.spring.security.spring.boot.jwt.exception.UserException;
import com.spring.security.spring.boot.jwt.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserEntity> findAll(){
        return userRepository.findAll();
    }

    @Transactional
    public void save(CreateUserDTO createUserDTO){
        Set<RoleEntity> roles = convertRoles(createUserDTO.getRoles());

        if(userRepository.existsByUsername(createUserDTO.getUsername())) {
            throw new UserException("El usuario " + createUserDTO.getUsername() + " ya existe");
        }

        UserEntity user = UserEntity.builder()
                .id(null)
                .username(createUserDTO.getUsername())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .roles(roles)
                .build();

        userRepository.save(user);
    }

    @Transactional
    public void  update(Long userId, UpdateUserDTO updateUserDTO) {
        Set<RoleEntity> roles = convertRoles(updateUserDTO.getRoles());
        UserEntity userFind = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("Usuario no encontrado con ID: " + userId));
        if (userRepository.existsByUsername(updateUserDTO.getUsername()) && !userFind.getUsername().equals(updateUserDTO.getUsername())) {
            throw new UserException("El nombre de usuario ya está en uso");
        }

        UserEntity user = UserEntity.builder()
                .id(userId)
                .username(updateUserDTO.getUsername())
                .password(passwordEncoder.encode(updateUserDTO.getPassword()))
                .roles(roles)
                .build();
        userRepository.save(user);
    }

    @Transactional
    public void deleteById(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserException("Usuario no encontrado con ID: " + userId);
        }
        userRepository.deleteById(userId);
    }

    private Set<RoleEntity> convertRoles(List<String> roleNames) {
        return roleNames.stream()
                .map(roleName -> {
                    try {
                        ERole roleEnum = ERole.valueOf(roleName);
                        return RoleEntity.builder().id(null).name(roleEnum).build();
                    } catch (IllegalArgumentException e) {
                        throw new RoleException("Rol no válido: " + roleName);
                    }
                })
                .collect(Collectors.toSet());
    }
}
