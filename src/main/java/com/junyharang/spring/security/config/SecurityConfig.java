package com.junyharang.spring.security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Spring Security Config Class
 * <b>History: 2022.06.14 최초 작성</b>
 *
 * @author 주니하랑
 * @version 1.0.0
 */

@Slf4j @RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    /**
     * 이용자 생성 및 권한 설정
     *
     * @param auth - 세부 인가 처리 기능 설정 API 제공 객체
     */

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /* Memory 내에 임시 이용자 추가 */
        auth.inMemoryAuthentication().withUser("user").password("{noop}1111").roles("USER");
        auth.inMemoryAuthentication().withUser("sys").password("{noop}1111").roles("SYS", "USER");
        auth.inMemoryAuthentication().withUser("admin").password("{noop}1111").roles("ADMIN", "SYS", "USER");
    }

    /**
     * Spring Security 설정 Method
     *
     * @param http - 세부 보안 기능 설정 API 제공 객체
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /* 인가 처리 */
                .authorizeRequests()                                                                                // Client가 http 방식으로 요청을 보내면
                .antMatchers("/user").hasRole("USER")                                                   // 요청 이용자가 /user URI에 대한 요청을 하였을 때, 해당 URI 권한 중 USER 권한으로 이용자가 접근하였는지 확인.
                .antMatchers("/admin/pay").hasRole("ADMIN")                                             // 요청 이용자가 /admin/pay URI에 대한 요청을 하였을 때, 해당 URI 권한 중 ADMIN 권한으로 이용자가 접근하였는지 확인.
                .antMatchers("/admin/**").access("hasRole('ADMIN') or hasRole('SYS')")          // 요청 이용자가 /admin 하위 URI에 대한 요청을 하였을 때, 해당 URI 권한 중 ADMIN과 SYS 권한으로 이용자가 접근하였는지 확인.
                .anyRequest().authenticated()                                                                       // 모든 요청에 대해 인증 검사를 수행한다.
                .and()                                                                                              // 그리고,
                .formLogin()                                                                                        // 인증 방식은 formLogin 방식
//              .loginPage("/signin")                                                                               // 개발자 정의 Login Page 설정
                .defaultSuccessUrl("/")                                                                             // Login 성공 시 이동할 위치
                .failureUrl("/signin")                                                           // Login 실패 시 이동할 위치
                .usernameParameter("userId")                                                                        // 회원 ID를 입력 받을 변수 이름 정의 (기본은 username이나, 변경 가능)
                .passwordParameter("password")                                                                      // 회원 비밀번호 입력 받을 변수 이름 정의 (기본은 password이나, 변경 가능)
                .loginProcessingUrl("/signin/proc")                                                                 // Login Form Action URL (기본은 "/login" 이며, 강의에서는 "/login_proc" 으로 설정)
                .successHandler(new AuthenticationSuccessHandler() {                                                // Login 성공 시 호출 될 Handler 정의
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

                        log.info("WebSecurityConfigurerAdapter 구현체 SecurityConfig의 configure(HttpSecurity http)가 호출 되었습니다!");
                        log.info("이용자가 인증이 성공하여 인증 성공 처리를 위한 Handler onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)가 호출 되었습니다!");
                        log.info("인증 성공 이용자 정보 : " + authentication.getName());

                        log.info("인증 성공 이용자를 root Page로 이동 시키겠습니다!");
                        response.sendRedirect("/");

                    } // onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 끝
                }) // successHandler(new AuthenticationSuccessHandler() 끝

                .failureHandler(new AuthenticationFailureHandler() {    // Login 실패 시 호출 될 Handler 정의
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

                        log.info("WebSecurityConfigurerAdapter 구현체 SecurityConfig의 configure(HttpSecurity http)가 호출 되었습니다!");
                        log.info("이용자가 인증이 실패하여 인증 실패 처리를 위한 Handler onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)가 호출 되었습니다!");
                        log.info("Exception 정보 : " + exception.getMessage());

                        log.info("인증 실패 이용자를 Login Page로 이동 시키겠습니다!");
                        response.sendRedirect("/signin");

                    }   // onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) 끝
                }) // failureHandler(new AuthenticationFailureHandler() 끝
                .permitAll()   // signin Page는 인증 받지 않은 이용자도 접속할 수 있게 permitAll 처리

                /* Logout 기능 활성화 */

                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/signin")
                .addLogoutHandler(new LogoutHandler() {
                    @Override
                    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                        // Session 무효화 작업
                        HttpSession session = request.getSession();
                        session.invalidate();
                    } // logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 끝
                }).logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        // Signin Page 이동 처리
                        response.sendRedirect("/signin");
                    } // onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 끝
                }).deleteCookies("remember-me")

                /* Remember Me 기능 활성 */

                .and()
                .rememberMe()
                .rememberMeParameter("remember")
                .tokenValiditySeconds(3600)       // 1시간 설정
                .userDetailsService(userDetailsService)

                /* The same time Session Control strategy - 동시 세션 제어 전략 */

                .and()
                .sessionManagement()
                .maximumSessions(1)                     // 최대 Session 허용 개수 1개
                .maxSessionsPreventsLogin(true);        // 현재 이용자 인증을 실패하도록 하는 전략
//                .maxSessionsPreventsLogin(false);       // 이전 이용자 Session을 만료 시키는 전략 - Default Value

                /* Session 고정 보호 기능 활성화 */
        http
                .sessionManagement()
                .sessionFixation().changeSessionId();   // 이용자가 인증에 성공하게 되면 해당 이용자에 Session은 그대로 두고, Session ID 값만 변경

    }
}
