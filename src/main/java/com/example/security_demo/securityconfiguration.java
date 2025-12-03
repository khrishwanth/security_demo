package com.example.security_demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;


@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class securityconfiguration {
      @Autowired
      UserDetailsService service;

      @Autowired
     JwtFilter filter;


    @Bean
    public SecurityFilterChain config (HttpSecurity http)  throws Exception{
             http.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(List.of("http://localhost:5173/"));
            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
            config.setAllowedHeaders(List.of("*"));
            config.setAllowCredentials(true);
            return config;
        }));
         http.csrf(Customizer -> Customizer.disable());
       http.authorizeHttpRequests(request -> request.requestMatchers("login","reg").permitAll().anyRequest().authenticated());
       http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
         .addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class);
         return http.build();
    }
    
    @Bean
    public AuthenticationProvider authProvider(){
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider(service);
        dao.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return dao;
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception{
         return config.getAuthenticationManager();
    }
}


