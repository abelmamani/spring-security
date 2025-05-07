package com.spring.security.spring.boot.jwt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    public String hello(){
        return "Hello, i'm ADMIN Or USER";
    }
}
