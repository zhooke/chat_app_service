package com.tensua.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * SecuritySecureConfig配置
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecuritySecureConfig {

    private final String adminContextPath;

    public SecuritySecureConfig(AdminServerProperties adminServerProperties) {
        this.adminContextPath = adminServerProperties.getContextPath();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // 登录成功处理类
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(adminContextPath + "/applications");
        http.authorizeRequests()
                //静态文件允许访问
                .requestMatchers(adminContextPath + "/assets/**").permitAll()
                //登录页面允许访问
                .requestMatchers(adminContextPath + "/login", "/css/**", "/js/**", "/image/*").permitAll()
                //其他所有请求需要登录
                .anyRequest().authenticated()
                .and()
                //登录页面配置，用于替换security默认页面
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.loginPage(adminContextPath + "/login").successHandler(successHandler))
                //登出页面配置，用于替换security默认页面
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutUrl(adminContextPath + "/logout"))
                .httpBasic(Customizer.withDefaults())
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers(
                                adminContextPath + "/instances",
                                adminContextPath + "/actuator/**",
                                adminContextPath + "/logout"));

        return http.build();
    }

}