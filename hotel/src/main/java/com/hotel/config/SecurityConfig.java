package com.hotel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // habilita @PreAuthorize (roles por método)
@RequiredArgsConstructor
@SuppressWarnings("java:S4502")
public class SecurityConfig {

        private final JwtFilter jwtFilter;
        private final UserDetailsService userDetailsService;

        private static final String RUTA_HOTEL = "/api/hotel/**";
        private static final String RUTA_HABITACION = "/api/habitacion/**";
        private static final String RUTA_HUESPED = "/api/huesped/**";
        private static final String RUTA_SERVICIOS = "/api/servicios/**";
        private static final String RUTA_RESERVAS = "/api/reservas/**";
        private static final String RUTA_REPORTES = "/api/reportes/**";
        private static final String RUTA_AUTH = "/api/auth/**";
        private static final String ADMIN = "ADMIN";
        private static final String USER = "USER";

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
                provider.setPasswordEncoder(passwordEncoder());
                return provider;
        }

        // El AuthenticationManager lo usa el login para verificar credenciales
        @Bean
        public AuthenticationManager authenticationManager(
                        AuthenticationConfiguration config) {
                return config.getAuthenticationManager();
        }

        // La cadena de seguridad: que se protege y que no
        @SuppressWarnings("null")
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) {

                http
                                .csrf(AbstractHttpConfigurer::disable)

                                .authorizeHttpRequests(auth -> auth

                                                // Login
                                                .requestMatchers(RUTA_AUTH).permitAll()

                                                // Swagger
                                                .requestMatchers(
                                                                "/swagger-ui/**",
                                                                "/swagger-ui.html",
                                                                "/v3/api-docs/**")
                                                .permitAll()

                                                // HOTEL
                                                .requestMatchers(HttpMethod.GET, RUTA_HOTEL).hasAnyRole(ADMIN, USER)
                                                .requestMatchers(HttpMethod.POST, RUTA_HOTEL).hasRole(ADMIN)
                                                .requestMatchers(HttpMethod.PUT, RUTA_HOTEL).hasRole(ADMIN)
                                                .requestMatchers(HttpMethod.DELETE, RUTA_HOTEL).hasRole(ADMIN)

                                                // HABITACIONES
                                                .requestMatchers(HttpMethod.GET, RUTA_HABITACION)
                                                .hasAnyRole(ADMIN, USER)
                                                .requestMatchers(HttpMethod.POST, RUTA_HABITACION).hasRole(ADMIN)
                                                .requestMatchers(HttpMethod.PUT, RUTA_HABITACION).hasRole(ADMIN)
                                                .requestMatchers(HttpMethod.DELETE, RUTA_HABITACION).hasRole(ADMIN)

                                                // HUÉSPEDES
                                                .requestMatchers(HttpMethod.GET, RUTA_HUESPED).hasAnyRole(ADMIN, USER)
                                                .requestMatchers(HttpMethod.POST, RUTA_HUESPED).hasRole(ADMIN)
                                                .requestMatchers(HttpMethod.PUT, RUTA_HUESPED).hasRole(ADMIN)
                                                .requestMatchers(HttpMethod.DELETE, RUTA_HUESPED).hasRole(ADMIN)

                                                // SERVICIOS
                                                .requestMatchers(HttpMethod.GET, RUTA_SERVICIOS).hasAnyRole(ADMIN, USER)
                                                .requestMatchers(HttpMethod.POST, RUTA_SERVICIOS).hasRole(ADMIN)
                                                .requestMatchers(HttpMethod.PUT, RUTA_SERVICIOS).hasRole(ADMIN)
                                                .requestMatchers(HttpMethod.PATCH, RUTA_SERVICIOS).hasRole(ADMIN)
                                                .requestMatchers(HttpMethod.DELETE, RUTA_SERVICIOS).hasRole(ADMIN)

                                                // RESERVAS
                                                .requestMatchers(HttpMethod.GET, RUTA_RESERVAS).hasAnyRole(ADMIN, USER)
                                                .requestMatchers(HttpMethod.POST, RUTA_RESERVAS).hasAnyRole(ADMIN, USER)
                                                .requestMatchers(HttpMethod.PUT, RUTA_RESERVAS).hasAnyRole(ADMIN, USER)
                                                .requestMatchers(HttpMethod.PATCH, RUTA_RESERVAS)
                                                .hasAnyRole(ADMIN, USER)
                                                .requestMatchers(HttpMethod.DELETE, RUTA_RESERVAS).hasRole(ADMIN)

                                                // REPORTES
                                                .requestMatchers(HttpMethod.GET, RUTA_REPORTES).hasRole(ADMIN)

                                                .anyRequest().authenticated())

                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                                .authenticationProvider(authenticationProvider())

                                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
