package com.example.demo.dtos;

public class RegisterUserDto {

    // Field to store the user's email
    private String email;

    // Field to store the user's password
    private String password;

    // Field to store the user's full name
    private String fullName;

    // Getter for the email field
    public String getEmail() {
        return email;
    }

    // Setter for the email field
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter for the fullName field
    public String getFullName() {
        return fullName;
    }

    // Setter for the fullName field
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    // Getter for the password field
    public String getPassword() {
        return password;
    }

    // Setter for the password field
    public void setPassword(String password) {
        this.password = password;
    }

    // Constructor to initialize the fields
    public RegisterUserDto(String email, String password, String fullName) {
        super();
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    // Default constructor
    public RegisterUserDto() {
        super();
        // TODO Auto-generated constructor stub
    }

    // Override toString method to return a string representation of the object
    @Override
    public String toString() {
        return "RegisterUserDto [email=" + email + ", password=" + password + "]";
    }
}
