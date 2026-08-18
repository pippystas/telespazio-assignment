package com.telespazio.pantry.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

        @Bean
        public InMemoryUserDetailsManager userDetailsService() {
                UserDetails user = User.withUsername("user")
                                .password("{noop}password")
                                .roles("USER")
                                .build();

                UserDetails admin = User.withUsername("admin")
                                .password("{noop}password")
                                .roles("ADMIN")
                                .build();

                return new InMemoryUserDetailsManager(user, admin);
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(HttpMethod.GET, "/api/items").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/api/items").hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.PATCH, "/api/items/*/restock")
                                                .hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/api/items/*").hasRole("ADMIN")
                                                .anyRequest().authenticated())
                                .httpBasic(withDefaults());

                return http.build();
        }

}
