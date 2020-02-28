package com.bbs.cart.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class CartDataSecurityConfiguration {
    @Bean
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {

        final ServerHttpSecurity httpSecurity = http.csrf().disable().logout().disable().formLogin().disable();
        return httpSecurity.build();
    }

}
