package com.junyharang.spring.security;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController public class SecurityController {

    @GetMapping("/") public String index() {
        return "Hello, JunyHarang đ";
    } // String index() ë

    @GetMapping("/signin") public String signIn() {
        return "ě´ ęłłě Login Page ěëë¤!";
    } // signIn() ë
} // class ë
