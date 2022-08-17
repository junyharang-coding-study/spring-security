package com.junyharang.spring.security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <b>Spring Security Config Class</b>
 *
 * @author 주니하랑
 * @version 1.1.0
 */

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    /**
     * <b>이용자 생성 및 권한 설정</b>
     *
     * @param auth 세부 인가 처리 기능 설정 API 제공 객체
     */

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /* Memory 내에 임시 이용자 추가 */
        auth.inMemoryAuthentication().withUser("user").password("{noop}1111").roles("USER");
        auth.inMemoryAuthentication().withUser("sys").password("{noop}1111").roles("SYS", "USER");
        auth.inMemoryAuthentication().withUser("admin").password("{noop}1111").roles("ADMIN", "SYS", "USER");
    }

    /**
     * <b>Spring Security 설정 Method</b>
     *
     * @param http 세부 보안 기능 설정 API 제공 객체
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/admin/pay").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin();
    }
}
