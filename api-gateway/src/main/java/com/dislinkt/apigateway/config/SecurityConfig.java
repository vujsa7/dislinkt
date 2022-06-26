package com.dislinkt.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain gatewaySecurityWebFilterChain(ServerHttpSecurity http){
        return http.csrf()
                .disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.GET, "/profile-service/profiles").permitAll()
                .pathMatchers(HttpMethod.GET,"/profile-service/profiles/{id}").permitAll()
                .pathMatchers(HttpMethod.GET,"/profile-service/profiles/username").permitAll()
                .pathMatchers(HttpMethod.POST, "/profile-service/profiles").permitAll()
                .pathMatchers(HttpMethod.GET,"/profile-service/profiles/{id}/image").permitAll()
                .pathMatchers(HttpMethod.GET,"/profile-service/profiles/name-and-image/{id}").permitAll()
                .pathMatchers(HttpMethod.GET,"/profile-service/storage/**").permitAll()
                .pathMatchers(HttpMethod.GET,"/post-service/posts/**").permitAll()
                .anyExchange().authenticated()
                .and()
                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt)
                .cors()
                .and()
                .build();
    }

}
