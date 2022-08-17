package com.junyharang.spring.security.config;

/**
 * <b>Spring Security Config Class</b>
 *
 * @author 주니하랑
 * @version 1.1.0
 */

//@Slf4j
//@RequiredArgsConstructor
//@Order(0)
//@Configuration
//@EnableWebSecurity
//public class SecurityMultiConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .antMatcher("/admin/**")
//                .authorizeRequests()
//                .anyRequest().authenticated()
//        .and()
//                .httpBasic();
//    }
//}
//
//@Order(1)
//@Configuration class SecurityMultiConfig2 extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .anyRequest().permitAll()
//        .and()
//                .formLogin();
//
//        /* 부모, 자식 Thread 간 ThreadLocal 공유를 위한 설정 */
//        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
//    }
//}
