package com.tensua.blogservice.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.tensua.blogservice.config.oauth.DaoUserDetailsService;
import com.tensua.blogservice.config.oauth.OAuthPreRequestFilter;
import com.tensua.blogservice.data.constant.SecurityConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.Resource;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Configuration
//开启springsecurity配置修改
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Value("${jwt.public.key}")
    RSAPublicKey key;

    @Value("${jwt.private.key}")
    RSAPrivateKey priv;

    @Resource
    private DaoUserDetailsService daoUserDetailsService;

    @Resource
    private OAuthPreRequestFilter oauthPreRequestFilter;//后面jwt验证需要用到的过滤器，现在先不理它

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)//禁用csrf保护，前后端分离不需要
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(SecurityConstant.WHITELIST).permitAll()
                        .anyRequest().access((authentication, object) -> {
                            // 获取当前的访问路径
                            String requestURI = object.getRequest().getRequestURI();
                            PathMatcher pathMatcher = new AntPathMatcher();
                            // 白名单请求直接放行
                            for (String url : SecurityConstant.WHITELIST) {
                                if (pathMatcher.match(url, requestURI)) {
                                    return new AuthorizationDecision(true);
                                }
                            }
                            // 获取访问该路径所需权限
                            Map<String, ConfigAttribute> permissionMap = SecurityConstant.PERMISSION_MAP;
                            List<ConfigAttribute> apiNeedPermissions = new ArrayList<>();
                            for (Map.Entry<String, ConfigAttribute> config : permissionMap.entrySet()) {
                                if (pathMatcher.match(config.getKey(), requestURI)) {
                                    apiNeedPermissions.add(config.getValue());
                                }
                            }
                            // 如果接口没有配置权限则直接放行
                            if (apiNeedPermissions.isEmpty()) {
                                log.info("没有配置权限直接通行");
                                return new AuthorizationDecision(true);
                            }
                            // 获取当前登录用户权限信息
                            Collection<? extends GrantedAuthority> authorities = authentication.get().getAuthorities();
                            // 判断当前用户是否有足够的权限访问
                            for (ConfigAttribute configAttribute : apiNeedPermissions) {
                                // 将访问所需资源和用户拥有资源进行比对
                                String needAuthority = configAttribute.getAttribute();
                                for (GrantedAuthority grantedAuthority : authorities) {
                                    if (needAuthority.trim().equals(grantedAuthority.getAuthority())) {
                                        // 权限匹配放行
                                        return new AuthorizationDecision(true);
                                    }
                                }
                            }
                            return new AuthorizationDecision(false);
                        })
                )
                .formLogin(AbstractHttpConfigurer::disable)//取消默认登录页面的使用
                .logout(AbstractHttpConfigurer::disable)//取消默认登出页面的使用
                .httpBasic(AbstractHttpConfigurer::disable)
                .oauth2ResourceServer((oauth2ResourceServer) -> oauth2ResourceServer
                        .jwt((jwt) -> jwt.decoder(jwtDecoder())))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//禁用session，因为我们已经使用了JWT

                .addFilterBefore(oauthPreRequestFilter, UsernamePasswordAuthenticationFilter.class)//将用户授权时用到的JWT校验过滤器添加进SecurityFilterChain中，并放在UsernamePasswordAuthenticationFilter的前面
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                )
                // 注册重写后的UserDetailsService实现
                .userDetailsService(daoUserDetailsService)
                // 注册自定义拦截器
        ;
        return http.build();
    }

    @Bean
    UserDetailsService users() {
        return daoUserDetailsService;
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.key).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.key).privateKey(this.priv).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    /**
     * 加密类
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 鉴权管理类
     */
    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(daoUserDetailsService);
        return provider;
    }
}