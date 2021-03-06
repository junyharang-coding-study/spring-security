package com.junyharang.spring.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring Security Config Class
 * <b>History:</b>
 * @author 주니하랑
 * @version 1.0.0, 2022.06.14 최초 작성
 */

@Slf4j
@Configuration @EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Spring Security 설정 Method
     * @param http - 세부 보안 기능 설정 API 제공 객체
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .authorizeRequests()                                    // Client가 http 방식으로 요청을 보내면
                    .anyRequest().authenticated()                           // 모든 요청에 대해 인증 검사를 수행한다.
                .and()                                                      // 그리고,
                    .formLogin()                                            // 인증 방식은 formLogin 방식
//                  .loginPage("/signin")                                   // 개발자 정의 Login Page 설정
                    .defaultSuccessUrl("/")                                 // Login 성공 시 이동할 위치
                    .failureUrl("/signin")              // Login 실패 시 이동할 위치
                    .usernameParameter("userId")                            // 회원 ID를 입력 받을 변수 이름 정의 (기본은 username이나, 변경 가능)
                    .passwordParameter("password")                          // 회원 비밀번호 입력 받을 변수 이름 정의 (기본은 password이나, 변경 가능)
                    .loginProcessingUrl("/signin/proc")                     // Login Form Action URL (기본은 "/login" 이며, 강의에서는 "/login_proc" 으로 설정)
                    .successHandler(new AuthenticationSuccessHandler() {    // Login 성공 시 호출 될 Handler 정의
                        @Override
                        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

                            log.info("WebSecurityConfigurerAdapter 구현체 SecurityConfig의 configure(HttpSecurity http)가 호출 되었습니다!");
                            log.info("이용자가 인증이 성공하여 인증 성공 처리를 위한 Handler onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)가 호출 되었습니다!");
                            log.info("인증 성공 이용자 정보 : " + authentication.getName() );

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
                .permitAll();   // signin Page는 인증 받지 않은 이용자도 접속할 수 있게 permitAll 처리

    } // configure(HttpSecurity http) 끝
} // class 끝
