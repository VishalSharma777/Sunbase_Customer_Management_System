package com.example.demo.customerServices;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.customerRepository.UserInterface;
import com.example.demo.dtos.LoginUserDto;
import com.example.demo.dtos.RegisterUserDto;
import com.example.demo.model.UserModel;

@Service
public class UserService {
    
    private final UserInterface userRepository; // Repository to handle user-related database operations
    private final PasswordEncoder passwordEncoder; // Encoder to hash user passwords
    private final AuthenticationManager authenticationManager; // Manages authentication processes

    // Constructor to inject dependencies
    public UserService(
            UserInterface userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user by saving the user data in the database.
     * 
     * @param input Data Transfer Object (DTO) containing registration data (full name, email, password)
     * @return The saved UserModel entity after registration
     */
    public UserModel signup(RegisterUserDto input) {
        // Create a new UserModel entity and set its fields based on the DTO input
        UserModel userModel = new UserModel();
        userModel.setFullName(input.getFullName());
        userModel.setEmail(input.getEmail());
        userModel.setPassword(passwordEncoder.encode(input.getPassword())); // Encrypt the password

        // Save the new user to the database and return the saved entity
        return userRepository.save(userModel);
    }

    /**
     * Authenticates a user by verifying their email and password.
     * 
     * @param input DTO containing login data (email, password)
     * @return The authenticated UserModel entity
     */
    public UserModel authenticate(LoginUserDto input) {
        // Perform authentication using the provided email and password
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                input.getEmail(),
                input.getPassword()
            )
        );

        // Retrieve the authenticated user from the database based on the email
        // If the user is not found, throw an exception
        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
