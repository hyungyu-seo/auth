package com.example.demo.config;

import com.example.demo.infrastructure.auth.JwtAuthenticationFilter;
import com.example.demo.infrastructure.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${aes.secret}")
    private String secret;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection for API usage
                .authorizeHttpRequests(auth -> auth // Swagger 관련 경로 허용
                        .requestMatchers("/**").permitAll()
                        .requestMatchers("/szs/scrap").hasRole("MEMBER")
                        .requestMatchers("/szs/refund").hasRole("MEMBER")// Public endpoints
                        .anyRequest().authenticated() // All other endpoints require authentication
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AesBytesEncryptor aesBytesEncryptor() {
        return new AesBytesEncryptor(secret, "70726574657374");
    }


    public Boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder().matches(rawPassword, encodedPassword);
    }

}
