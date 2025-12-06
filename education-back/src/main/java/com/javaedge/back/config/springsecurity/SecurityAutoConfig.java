//package com.javaedge.back.config.springsecurity;
//
//import jakarta.annotation.Resource;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
//import org.springframework.security.authorization.AuthorityAuthorizationManager;
//import org.springframework.security.authorization.AuthorizationDecision;
//import org.springframework.security.authorization.AuthorizationManager;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
//import org.springframework.security.web.access.intercept.RequestMatcherDelegatingAuthorizationManager;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
//import org.springframework.security.web.util.matcher.AndRequestMatcher;
//import org.springframework.security.web.util.matcher.AnyRequestMatcher;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
//
///**
// * @author 千祎来了
// * @date 2023/9/24 16:27
// * Spring Security 6.0 只需要 @EnableWebSecurity 即可添加过滤器
// * 旧版需要继承 WebSecurityConfigurationAdapter 类
// */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityAutoConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    // 这个自定义的过滤器
//    @Autowired
//    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
//
//    @Autowired
//    SecurityAccessDeniedHandler securityAccessDeniedHandler;
//
//    @Bean
//    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//        http
//                // 前后端分离架构不需要csrf保护
//                .csrf(AbstractHttpConfigurer::disable)
//                // 前后端分离是无状态的，不需要session了，直接禁用
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests((authorize) -> authorize
//                        .anyRequest().permitAll())
//                .exceptionHandling(exception -> {
//                    exception.accessDeniedHandler(securityAccessDeniedHandler);
//                });
//        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class); //把token校验过滤器添加到过滤器链中)
//        return http.build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//}