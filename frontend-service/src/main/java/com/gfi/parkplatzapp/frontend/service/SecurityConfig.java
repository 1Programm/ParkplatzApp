package com.gfi.parkplatzapp.frontend.service;

import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import reactor.netty.http.client.HttpClient;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain (ServerHttpSecurity http, ServerLogoutSuccessHandler logoutSuccessHandler) {
        http
                .authorizeExchange((exchange) -> exchange.anyExchange().authenticated())
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .oauth2Login(login -> {})
                .logout((logout) -> logout.logoutSuccessHandler(logoutSuccessHandler));
    
        return http.build();
    }

    @Bean
    public HttpClient createHttpClient(){
        return HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
    }

    @Bean
    public ServerLogoutSuccessHandler keycloakLogoutSuccessHandler(ReactiveClientRegistrationRepository repository){
        OidcClientInitiatedServerLogoutSuccessHandler handler = new OidcClientInitiatedServerLogoutSuccessHandler(repository);
        handler.setPostLogoutRedirectUri("{baseUrl}/buchen");

        return handler;
    }

}