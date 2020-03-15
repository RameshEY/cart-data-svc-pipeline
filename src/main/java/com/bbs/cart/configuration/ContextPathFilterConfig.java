package com.bbs.cart.configuration;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.WebFilter;

@Configuration
@EnableConfigurationProperties
public class ContextPathFilterConfig {
    @Bean
    public WebFilter contextPathWebFilter(final ServerProperties serverProperties) {
        String contextPath = serverProperties.getServlet().getContextPath();
        return (serverWebExchange, webFilterChain) -> {
            ServerHttpRequest request = serverWebExchange.getRequest();
            String path = request.getURI().getPath();
            if (path.contains(contextPath)) {
                return webFilterChain.filter(serverWebExchange.mutate()
                        .request(request.mutate().contextPath(contextPath).build()).build());
            }
            return webFilterChain.filter(serverWebExchange);
        };
    }
}
