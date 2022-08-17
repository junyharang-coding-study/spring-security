package com.junyharang.spring.security;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collection;

/**
 * <b>Spring Security Test Controller</b>
 *
 * @author 주니하랑
 * @version 1.1.0
 */

@RestController
public class SecurityController {

    @GetMapping("/")
    public String index() {
        return "Hello, JunyHarang 👋 <br><br> This is JunyHarang's Home <br><br> 요청 이용자 권한 정보 : " + getRoleInfo();
    } // String index() 끝

    @GetMapping("/signin")
    public String signIn() {
        return "이 곳은 Login Page 입니다! <br><br> 요청 이용자 권한 정보 : " + getRoleInfo();
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

    @GetMapping("/denied")
    public String deniedErrorPage(Principal principal) {
        return "401 Unauthorized 귀하에 권한으로 접근할 수 없는 Page 입니다. <br><br> 요청 이용자 ID 정보 : " + principal.getName() + " <br> 요청 이용자 권한 정보 : " + getRoleInfo();
    } // deniedErrorPage(Principal principal) 끝

    /**
     * <b>Main Thread의 자식 Thread를 만드는 Method</b>
     *
     * @return String - thread 문자열 반환
     */

    @GetMapping("/thread") public String thread() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                // SecurityContextHolder 안 SecurityContext에 저장되어 있는 인증 객체를 불러옴.
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            }
        }).start();
        return "thread";
    }

    /**
     * <b>이용자 권한 반환 Method</b>
     *
     * @return Collection<? extends GrantedAuthority> - 요청 이용자 보유 권한 목록
     */

    private Collection<? extends GrantedAuthority> getRoleInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getAuthorities();
    } // getRoleInfo() 끝

    /**
     * <b>Session에 저장된 이용자 권한 반환 Method</b>
     *
     * @param session 이용자 정보가 담긴 Session 객체
     * @return Authentication - 요청 이용자 보유 권한 목록
     */

    private Authentication getRoleInfoInSession(HttpSession session) {
        SecurityContext context = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

        return context.getAuthentication();
    }
} // class 끝
