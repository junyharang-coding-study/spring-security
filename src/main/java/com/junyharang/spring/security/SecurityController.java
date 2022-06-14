package com.junyharang.spring.security;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController public class SecurityController {

    @GetMapping("/") public String index() {
        return "Hello, JunyHarang 👋";
    } // String index() 끝

    @GetMapping("/signin") public String signIn() {
        return "이 곳은 Login Page 입니다!";
    } // signIn() 끝
} // class 끝
