package com.spring.security.spring.boot.jwt.utils;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseEntityManager {
    public static ResponseEntity<?> badRequest(String message){
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.badRequest().body(response);
    }
}
