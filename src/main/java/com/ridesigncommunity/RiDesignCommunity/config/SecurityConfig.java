package com.ridesigncommunity.RiDesignCommunity.config;

import com.ridesigncommunity.RiDesignCommunity.security.CustomUserDetailsService;
import com.ridesigncommunity.RiDesignCommunity.security.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(customUserDetailsService);
        return new ProviderManager(auth);
    }

    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers(HttpMethod.POST, "/users/authenticate").permitAll()
                        .requestMatchers(HttpMethod.POST, "/products").permitAll()
                        .requestMatchers(HttpMethod.GET, "/products/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/{id}").hasAnyRole("ADMIN","USER")
                        .requestMatchers(HttpMethod.GET, "/inquiries").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/users/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/{username}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/users/{username}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/users/{username}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/users/addFavorite/{username}/{productId}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/users/removeFavorite/{username}/{productId}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/image/product/{productId}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/image/user/{username}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/image/product/{productId}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/image/user/{username}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/inquiries").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/inquiries").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/inquiries/{inquiryId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/shopping-cart/user/{username}/products/{productId}/add-to-cart").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/shopping-cart/user/{username}/products").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/shopping-cart/user/{userId}/remove-from-cart/{productId}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/authenticate").permitAll()
                        .requestMatchers("/authenticated").permitAll()
                        .requestMatchers("/products/**").permitAll()
                        .requestMatchers("/profile").authenticated()
                        .requestMatchers("/shopping-cart").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/profile").authenticated()
                        .requestMatchers("/admins").hasAuthority("ROLE_ADMIN")
                        .anyRequest().denyAll()
                )
                .sessionManagement(SESS -> SESS.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}


