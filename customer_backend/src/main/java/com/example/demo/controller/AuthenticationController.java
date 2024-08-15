package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.customerServices.AuthenticationService;
import com.example.demo.customerServices.JwtService;
import com.example.demo.dtos.LoginResponse;
import com.example.demo.dtos.LoginUserDto;
import com.example.demo.dtos.RegisterUserDto;
import com.example.demo.model.User;

/**
 * AuthenticationController handles user authentication and registration requests.
 * It exposes public endpoints for user registration and login.
 */
@RestController
@RequestMapping("/api/v1/customers/public")
public class AuthenticationController {
  
    // Injecting JwtService and AuthenticationService dependencies
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    // Constructor for dependency injection
    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    /**
     * Register a new user.
     * @param registerUserDto The registration details (DTO).
     * @return ResponseEntity containing the registered user details.
     */
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        // Register the user using the authentication service
        User registeredUser = authenticationService.signup(registerUserDto);

        // Return the registered user details with an HTTP 200 status
        return ResponseEntity.ok(registeredUser);
    }

    /**
     * Authenticate a user (login).
     * @param loginUserDto The login details (DTO).
     * @return ResponseEntity containing the JWT token and expiration time or an error message if authentication fails.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginUserDto loginUserDto) {
        try {
            // Authenticate the user using the authentication service
            User authenticatedUser = authenticationService.authenticate(loginUserDto);

            // Generate a JWT token for the authenticated user
            String jwtToken = jwtService.generateToken(authenticatedUser);

            // Create a response with the JWT token and its expiration time
            LoginResponse loginResponse = new LoginResponse()
                    .setToken(jwtToken)
                    .setExpiresIn(jwtService.getExpirationTime());

            // Return the login response with an HTTP 200 status
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            // Handle exceptions during authentication and return an HTTP 401 status with an error message
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
