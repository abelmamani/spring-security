package com.spring.security.spring.boot.jwt.service;

import com.spring.security.spring.boot.jwt.dto.LoginDTO;
import com.spring.security.spring.boot.jwt.entity.UserEntity;
import com.spring.security.spring.boot.jwt.exception.UserException;
import com.spring.security.spring.boot.jwt.repository.UserRepository;
import com.spring.security.spring.boot.jwt.security.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserRepository userRepository;

    public String login(@RequestBody LoginDTO loginDTO) {
        UserEntity user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new UserException("El usuario "+loginDTO.getUsername()+" no existe."));
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtService.generateToken(user);
    }
}
