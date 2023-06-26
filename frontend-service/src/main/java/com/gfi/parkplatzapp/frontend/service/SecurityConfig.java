package com.gfi.parkplatzapp.frontend.service;

import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.header.ReferrerPolicyServerHttpHeadersWriter;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter;
import reactor.netty.http.client.HttpClient;

@Configuration
public class SecurityConfig {

    private static final String appId = "PA";
    private static final String csp = """
    default-src 'self' 'unsafe-eval' https://cdnjs.cloudflare.com/ajax/libs/pdf.js/2.2.228/pdf.worker.min.js;
    style-src 'self' 'unsafe-inline' https://use.fontawesome.com https://fonts.googleapis.com;
    font-src 'self' data: https://use.fontawesome.com https://fonts.googleapis.com https://fonts.gstatic.com;
    worker-src 'self' blob:;
    connect-src http://localhost:8080 http://localhost:8081 http://localhost:4200 ws://localhost:4200 blob:;
    img-src 'self' data: blob:""";

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain (ServerHttpSecurity http) {
        http
            .authorizeExchange()
            .anyExchange()
            .authenticated()
            .and()
                .csrf().disable()
            .oauth2Login(); // to redirect to oauth2 login page.
//        http.authorizeExchange().anyExchange().permitAll();

        CookieServerCsrfTokenRepository csrfRepository = CookieServerCsrfTokenRepository.withHttpOnlyFalse();
        csrfRepository.setCookieName(appId.toUpperCase() + "_XSRF-TOKEN");
        csrfRepository.setHeaderName("X-" + appId.toUpperCase() + "_XSRF-TOKEN");

        http.oauth2Login();

        http.csrf().csrfTokenRepository(csrfRepository).and()
                .headers()
                .xssProtection().disable()
//                .writer(xssWriter)
                .referrerPolicy(ReferrerPolicyServerHttpHeadersWriter.ReferrerPolicy.NO_REFERRER).and()
//                .contentSecurityPolicy(csp).and()
                .frameOptions().mode(XFrameOptionsServerHttpHeadersWriter.Mode.SAMEORIGIN);

        return http.build();
    }

//    @Bean
//    public HttpClient createHttpClient(){
//        return HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
//    }

}