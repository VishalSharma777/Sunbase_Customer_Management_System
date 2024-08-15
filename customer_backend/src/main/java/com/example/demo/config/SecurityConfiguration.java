package com.example.demo.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * SecurityConfiguration class configures Spring Security settings for the application.
 * It includes JWT-based authentication, CORS settings, and session management.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    // Injecting dependencies for authentication and JWT filtering
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Constructor for dependency injection
    public SecurityConfiguration(
        JwtAuthenticationFilter jwtAuthenticationFilter,
        AuthenticationProvider authenticationProvider
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configures the security filter chain for handling HTTP security.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disabling CSRF (Cross-Site Request Forgery) protection since it's not needed for stateless APIs
            .csrf(csrf -> csrf.disable())

            // Enabling CORS (Cross-Origin Resource Sharing) with custom configuration
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // Configuring authorization rules for requests
            .authorizeHttpRequests(authz -> authz
                // Permitting all requests to public customer-related endpoints
                .requestMatchers("/api/v1/customers/public/**").permitAll()
                // Requiring authentication for all other requests
                .anyRequest().authenticated()
            )

            // Configuring session management to be stateless (no sessions stored on the server)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Configuring the authentication provider to handle authentication
            .authenticationProvider(authenticationProvider)

            // Adding the JWT authentication filter before the UsernamePasswordAuthenticationFilter
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // Building the security filter chain
        return http.build();
    }

    /**
     * Configures CORS settings to allow specific origins, methods, and headers.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        // Create a new CORS configuration
        CorsConfiguration configuration = new CorsConfiguration();

        // Allowing requests from the specified origin (frontend application)
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));

        // Allowing specific HTTP methods
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Allowing specific headers, including Authorization and Content-Type
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        // Registering the CORS configuration for all URLs
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    /**
     * Bean definition for RestTemplate, used for making HTTP requests from the application.
     */
    @Bean
    public RestTemplate restemplate() {
        return new RestTemplate();
    }
}
