package com.gfi.parkplatzapp.backend;

import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RegexServerWebExchangeMatcher implements ServerWebExchangeMatcher {
    private final Pattern pattern;
    private final HttpMethod method;

    public RegexServerWebExchangeMatcher(String... patterns) {
        this(null, patterns);
    }

    public RegexServerWebExchangeMatcher(HttpMethod method, String... patterns) {
        this.pattern = Pattern.compile(Arrays.stream(patterns).map(p -> "(^" + p + "$)").collect(Collectors.joining("|")));
        this.method = method;
    }

    @Override
    public Mono<MatchResult> matches(ServerWebExchange serverWebExchange) {
        ServerHttpRequest request = serverWebExchange.getRequest();

        if(this.method != null && !this.method.equals(request.getMethod())) {
            return MatchResult.notMatch();
        }
        else {
            String uri = request.getPath().pathWithinApplication().toString();

            Matcher matcher = pattern.matcher(uri);

            return matcher.find() ? MatchResult.match() : MatchResult.notMatch();
        }
    }
}