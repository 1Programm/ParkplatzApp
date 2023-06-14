package com.gfi.parkplatzapp.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSession;
import org.springframework.session.ReactiveMapSessionRepository;
import org.springframework.session.ReactiveSessionRepository;
import org.springframework.session.config.annotation.web.server.EnableSpringWebSession;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableSpringWebSession
public class SpringHttpSessionConfig {

    @Bean
    public ReactiveSessionRepository<MapSession> reactiveSessionRepository(@Autowired SessionProperties props){
        ReactiveMapSessionRepository repo = new ReactiveMapSessionRepository(new ConcurrentHashMap<>());
        repo.setDefaultMaxInactiveInterval(props.getTimeout());
        return repo;
    }
}
