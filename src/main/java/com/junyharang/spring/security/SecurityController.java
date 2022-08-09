package com.junyharang.spring.security;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;

@RestController
public class SecurityController {

    @GetMapping("/")
    public String index(Principal principal) {
        return "Hello, JunyHarang 👋 This is JunyHarang's Home <br><br> 요청 이용자 ID 정보 : " + principal.getName() + " <br> 요청 이용자 권한 정보 : " + getRoleInfo();
    } // String index() 끝

    @GetMapping("/signin")
    public String signIn() {
        return "이 곳은 Login Page 입니다!";
    } // signIn() 끝

    @GetMapping("/user")
    public String user(Principal principal) {
        return "This is JunyHarang's user Page <br><br> 요청 이용자 ID 정보 : " + principal.getName() + " <br> 요청 이용자 권한 정보 : " + getRoleInfo();
    } // user() 끝

    @GetMapping("/admin/pay")
    public String adminPay(Principal principal) {
        return "This is JunyHarang's pay Page <br><br> 요청 이용자 ID 정보 : " + principal.getName() + " <br> 요청 이용자 권한 정보 : " + getRoleInfo();
    } // adminPay() 끝

    @GetMapping("/admin/test")
    public String adminTest(Principal principal) {
        return "This is JunyHarang's admin Test Page <br><br> 요청 이용자 ID 정보 : " + principal.getName() + " <br> 요청 이용자 권한 정보 : " + getRoleInfo();
    } // adminTest() 끝

    @GetMapping("/admin/management")
    public String adminManagement(Principal principal) {
        return "This is JunyHarang's admin Management Page <br><br> 요청 이용자 ID 정보 : " + principal.getName() + " <br> 요청 이용자 권한 정보 : " + getRoleInfo();
    } // adminManagement() 끝

    /**
     * 이용자 권한 반환 Method
     *
     * @return Collection<? extends GrantedAuthority> - 요청 이용자 보유 권한 목록
     */

    private Collection<? extends GrantedAuthority> getRoleInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getAuthorities();
    } // getRoleInfo() 끝
} // class 끝
