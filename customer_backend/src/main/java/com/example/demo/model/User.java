package com.example.demo.model;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a user in the system and implements UserDetails for Spring Security.
 */
@Entity
@Table(name="Users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for the user

    private String email; // Email address of the user

    private String password; // Password of the user

    private String FullName; // Full name of the user

    // Default constructor
    public User() {
        super();
        // Required by JPA
    }

    // Parameterized constructor for creating a User instance
    public User(Long id, String email, String password, String FullName) {
        super();
        this.id = id;
        this.email = email;
        this.password = password;
        this.FullName = FullName;
    }

    /**
     * Returns the authorities granted to the user. In this implementation, it's empty.
     * 
     * @return a collection of GrantedAuthority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(); // No authorities assigned in this example
    }

    /**
     * Returns the username of the user. In this case, it's the user's email.
     * 
     * @return the username
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Indicates whether the user's account has expired. In this case, it's always true.
     * 
     * @return true if the account is non-expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return true; // Account is never expired
    }

    /**
     * Indicates whether the user's account is locked. In this case, it's always true.
     * 
     * @return true if the account is non-locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true; // Account is never locked
    }

    /**
     * Indicates whether the user's credentials have expired. In this case, it's always true.
     * 
     * @return true if the credentials are non-expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Credentials are never expired
    }

    /**
     * Indicates whether the user is enabled. In this case, it's always true.
     * 
     * @return true if the user is enabled
     */
    @Override
    public boolean isEnabled() {
        return true; // User is always enabled
    }

    // Getters and setters
    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", password=" + password + "]";
    }
}
