package com.spring.security.spring.boot.jwt.controller;

import com.spring.security.spring.boot.jwt.dto.LoginDTO;
import com.spring.security.spring.boot.jwt.service.AuthService;
import com.spring.security.spring.boot.jwt.utils.ResponseEntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authServicee;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO request) {
        Map<String, String> response = new HashMap<>();
        try {
            String token = authServicee.login(request);
            response.put("token", token);
            return ResponseEntity.ok(response);
        }catch (Exception e) {
            return ResponseEntityManager.badRequest(e.getMessage());
        }
    }
}
