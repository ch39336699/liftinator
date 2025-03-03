package com.capacity.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/*
 * Project: liftinator
 * File: SecurityConfig.java
 * Author: Chris Harper
 * The SecurityConfig class is a configuration class for securing the API using Spring Security.
 * It defines how HTTP requests are authorized and which routes are accessible without authentication.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.csrf(AbstractHttpConfigurer::disable)  // Disable CSRF for stateless APIs
                .authorizeHttpRequests(auth-> //  auth.requestMatchers("/**").permitAll()
                        auth.requestMatchers("/elevatorAction/**").permitAll()
                                .anyRequest().authenticated()
                ).build();
    }
}
