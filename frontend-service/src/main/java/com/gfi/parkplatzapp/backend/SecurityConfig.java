package com.gfi.parkplatzapp.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.EnableWebFlux;

@Configuration
@EnableWebFlux
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        String[] angularApi = {
                "/(polyfills|vendor|main|runtime|common)(-es5|-es2015)?(\\.[a-f0-9]{20})?\\.js(\\.map)?",
                "/.*(-es5|-es2015)?(\\.[a-f0-9]{20})?.js(\\.map)?",
                "/styles(\\.[a-f0-9]{20})?\\.css", "/assets/.*",
                "/MaterialIcons-Regular(\\.[a-f0-9]{20})?\\.(eot|svg|woff|woff2|ttf)?\\??",
                "/(fa-solid|fa-brand|fa-regular|fa-light)-([0-9]{2,4})?(\\.[a-f0-9]{20})?\\.(eot|svg|woff|woff2|ttf)?\\??",
                "/styles(-es5|-es2015)?\\.(js|css)(\\.map)?" };


        http.authorizeExchange().matchers(new RegexServerWebExchangeMatcher(angularApi)).permitAll();

        http.authorizeExchange().pathMatchers("/logout").authenticated();

        http.authorizeExchange().pathMatchers("/test").permitAll();
        http.authorizeExchange().pathMatchers("/profil").hasRole("PA_ADMIN");
//        http.authorizeExchange().pathMatchers("/fb/api/test").authenticated();
        http.logout().logoutUrl("/logout");
        http.authorizeExchange().anyExchange().authenticated();
        http.oauth2Login();


        return http.build();
    }
}
