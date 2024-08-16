package com.example.demo.config;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.example.demo.customerServices.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JwtFilter is a custom security filter that intercepts each HTTP request 
 * to check for a valid JWT token. If a valid token is found, it authenticates the user.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    // HandlerExceptionResolver is used to handle exceptions within the filter
    private final HandlerExceptionResolver handlerExceptionResolver;

    // JwtService is a custom service for working with JWT tokens (e.g., extracting username)
    private final JwtService jwtService;

    // UserDetailsService is a Spring Security service that loads user-specific data
    private final UserDetailsService userDetailsService;

    // Constructor injection for dependencies
    public JwtFilter(
        JwtService jwtService,
        UserDetailsService userDetailsService,
        HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    /**
     * This method is called for each HTTP request. It checks if the request contains a valid JWT token,
     * and if so, it authenticates the user based on the token.
     */
    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // Get the Authorization header from the request
        final String authHeader = request.getHeader("Authorization");

        // If the Authorization header is missing or doesn't start with "Bearer ", continue without authentication
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Extract the JWT token from the Authorization header
            final String jwt = authHeader.substring(7);
            
            // Extract the username from the JWT token using JwtService
            final String userEmail = jwtService.extractUsername(jwt);

            // Get the current authentication from the SecurityContext
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // If the username is not null and no authentication is present, proceed with user authentication
            if (userEmail != null && authentication == null) {
                // Load user details using UserDetailsService
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                // Validate the token with the loaded user details
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // Create an authentication token with the user details and authorities
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    // Set additional details for the authentication token (e.g., request details)
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the authentication in the SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            // Continue with the next filter in the chain
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            // Handle any exceptions that occur during the filter process
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}
