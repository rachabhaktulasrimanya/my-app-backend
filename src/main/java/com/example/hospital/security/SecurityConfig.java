package com.example.hospital.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth

                    // ==============================
                    // PUBLIC APIs
                    // ==============================
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/api/schedule/**").permitAll()
                    .requestMatchers("/api/medical-records/**").permitAll()

                    // ==============================
                    // NEW FEATURES
                    // ==============================

                    // Doctor Reviews
                    .requestMatchers("/api/reviews/**")
                    .hasAnyRole("ADMIN","DOCTOR","PATIENT")

                    // Admin Analytics
                    .requestMatchers("/api/admin/analytics/**")
                    .hasRole("ADMIN")

                    // ==============================
                    // EXISTING RULES
                    // ==============================

                    // Admin APIs
                    .requestMatchers("/api/admin/**")
                    .hasRole("ADMIN")

                    // Doctors READ
                    .requestMatchers(HttpMethod.GET, "/api/doctors/**")
                    .hasAnyRole("ADMIN","DOCTOR","PATIENT")

                    // Doctors CREATE / DELETE
                    .requestMatchers(HttpMethod.POST, "/api/doctors/**")
                    .hasRole("ADMIN")

                    .requestMatchers(HttpMethod.DELETE, "/api/doctors/**")
                    .hasRole("ADMIN")

                    // Patients
                    .requestMatchers("/api/patients/**")
                    .hasAnyRole("ADMIN","PATIENT")

                    // Doctor appointments
                    .requestMatchers("/api/appointments/doctor/**")
                    .hasRole("DOCTOR")

                    // Appointments
                    .requestMatchers("/api/appointments")
                    .hasAnyRole("ADMIN","DOCTOR","PATIENT")

                    .requestMatchers("/api/appointments/**")
                    .hasAnyRole("ADMIN","DOCTOR","PATIENT")

                    // ==============================
                    // LAST RULE (MUST BE LAST)
                    // ==============================
                    .anyRequest().authenticated()
            )

            .addFilterBefore(jwtFilter,
                    UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}