package com.example.aniamlwaruser.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;


    @Bean
    public SecurityFilterChain
        securityFilterChain(HttpSecurity security) throws Exception {
        security.csrf(AbstractHttpConfigurer::disable);
        security.sessionManagement(
                configurer -> configurer
                        .sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS));
        security.authenticationProvider(authenticationProvider);
        security.addFilterBefore(jwtAuthenticationFilter
                , UsernamePasswordAuthenticationFilter.class);

        security.authorizeHttpRequests(req ->
                req.requestMatchers(
                       AntPathRequestMatcher.antMatcher("/api/v1/auth/**")
                       ,AntPathRequestMatcher.antMatcher("/api/v1/user/**")
                        ,AntPathRequestMatcher.antMatcher("/api/v1/user/terrain")
                       ,AntPathRequestMatcher.antMatcher("/api/v1/rank/**")
                       ,AntPathRequestMatcher.antMatcher("/api/v1/exchange/**")
                                ,AntPathRequestMatcher.antMatcher("/api/v1/inventory/**")
                                ,AntPathRequestMatcher.antMatcher("/api/v1/inventory/animals")
                                ,AntPathRequestMatcher.antMatcher("/api/v1/inventory/buildings")
                )
                        .permitAll()
                        .anyRequest().authenticated()


        );
        return security.build();

    }

}
