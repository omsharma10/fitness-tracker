package com.fittrack.fitness_tracker.config;

import com.fittrack.fitness_tracker.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // =========================
    // PASSWORD ENCODER
    // =========================

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // =========================
    // SECURITY FILTER CHAIN
    // =========================

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http

                // =========================
                // CSRF
                // =========================
                .csrf(csrf -> csrf.disable())

                // =========================
                // USER DETAILS SERVICE
                // =========================
                .userDetailsService(userDetailsService)

                // =========================
                // AUTHORIZATION
                // =========================
                .authorizeHttpRequests(auth -> auth

                        // -------------------------
                        // PUBLIC PAGES
                        // -------------------------
                        .requestMatchers(
                                "/",
                                "/login",
                                "/register"
                        ).permitAll()

                        // -------------------------
                        // PUBLIC REGISTRATION API
                        // -------------------------
                        .requestMatchers(
                                "/api/users/register"
                        ).permitAll()

                        // -------------------------
                        // STATIC FILES
                        // -------------------------
                        .requestMatchers(
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/favicon.ico"
                        ).permitAll()

                        // -------------------------
                        // ADMIN PAGE
                        // -------------------------
                        .requestMatchers(
                                "/admin"
                        ).hasRole("ADMIN")

                        // -------------------------
                        // ADMIN APIs
                        // -------------------------
                        .requestMatchers(
                                "/api/admin/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                "/api/settings/**"
                        ).hasRole("ADMIN")

                        // -------------------------
                        // CONTENT ADMIN ACTIONS
                        // -------------------------
                        .requestMatchers(
                                "/api/content/*/approve",
                                "/api/content/*/reject"
                        ).hasRole("ADMIN")

                        // -------------------------
                        // USER APIs
                        // -------------------------
                        .requestMatchers(
                                "/api/users/**"
                        ).authenticated()

                        // -------------------------
                        // WORKOUT APIs
                        // -------------------------
                        .requestMatchers(
                                "/api/workouts/**"
                        ).authenticated()

                        // -------------------------
                        // FITNESS GOAL APIs
                        // -------------------------
                        .requestMatchers(
                                "/api/goals/**"
                        ).authenticated()

                        // -------------------------
                        // CHALLENGE APIs
                        // -------------------------
                        .requestMatchers(
                                "/api/challenges/**"
                        ).authenticated()

                        // -------------------------
                        // CHALLENGE PARTICIPATION
                        // -------------------------
                        .requestMatchers(
                                "/api/challenge-participants/**"
                        ).authenticated()

                        // -------------------------
                        // FITNESS CONTENT
                        // -------------------------
                        .requestMatchers(
                                "/api/content/**"
                        ).authenticated()

                        // -------------------------
                        // ACTIVITY LOGS
                        // -------------------------
                        .requestMatchers(
                                "/api/activity-logs/**"
                        ).authenticated()

                        // -------------------------
                        // ALL OTHER REQUESTS
                        // -------------------------
                        .anyRequest().authenticated()
                )

                // =========================
                // LOGIN
                // =========================
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl(
                                "/dashboard",
                                true
                        )
                        .permitAll()
                )

                // =========================
                // LOGOUT
                // =========================
                .logout(logout -> logout
                        .logoutSuccessUrl(
                                "/login?logout"
                        )
                        .permitAll()
                );

        return http.build();
    }
}