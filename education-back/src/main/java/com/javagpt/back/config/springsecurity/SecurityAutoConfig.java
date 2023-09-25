package com.javagpt.back.config.springsecurity;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.access.intercept.RequestMatcherDelegatingAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 * @author 千祎来了
 * @date 2023/9/24 16:27
 * Spring Security 6.0 只需要 @EnableWebSecurity 即可添加过滤器
 * 旧版需要继承 WebSecurityConfigurationAdapter 类
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityAutoConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 这个自定义的过滤器
    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/user/login").anonymous()
//                .anyRequest()
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll()
                .anyRequest().authenticated()
                ;
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class); //把token校验过滤器添加到过滤器链中)
//        http
//                .csrf()//关闭session
//                .disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
////                .authenticationManager()
////                .authorizeHttpRequests()
////                .requestMatchers("/**")//  放行/**下的所有请求
////                .permitAll()
////                .anyRequest()
////                .authenticated()//  剩下的请求都需要拦截
////                .and()
////                .authorizeRequests()
//                // 对于登录接口 允许匿名访问
////                .requestMatchers("/user/login").anonymous()
////                .and()
//                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class) //把token校验过滤器添加到过滤器链中
//        ;

        return http.build();
    }



//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setPasswordEncoder(passwordEncoder());
//        authProvider.setUserDetailsService(null);
//        return authProvider;
//    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
//    @Bean
//    AuthorizationManager<RequestAuthorizationContext> requestMatcherAuthorizationManager(HandlerMappingIntrospector introspector) {
//        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
//        RequestMatcher permitAll =
//                new AndRequestMatcher(
//                        mvcMatcherBuilder.pattern("/resources/**"),
//                        mvcMatcherBuilder.pattern("/signup"),
//                        mvcMatcherBuilder.pattern("/about"));
//        RequestMatcher admin = mvcMatcherBuilder.pattern("/admin/**");
//        RequestMatcher db = mvcMatcherBuilder.pattern("/db/**");
//        RequestMatcher any = AnyRequestMatcher.INSTANCE;
//        AuthorizationManager<HttpServletRequest> manager = RequestMatcherDelegatingAuthorizationManager.builder()
//                .add(permitAll, (context) -> new AuthorizationDecision(true))
//                .add(admin, AuthorityAuthorizationManager.hasRole("ADMIN"))
//                .add(db, AuthorityAuthorizationManager.hasRole("DBA"))
//                .add(any, new AuthenticatedAuthorizationManager())
//                .build();
//        return (context) -> manager.check(context.getRequest());
//    }
}
