package com.junyharang.spring.security;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController public class SecurityController {

    @GetMapping("/") public String index() {
        return "Hello, JunyHarang 👋";
    } // String index() 끝
} // class 끝
