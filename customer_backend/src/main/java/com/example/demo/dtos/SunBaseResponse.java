package com.example.demo.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SunBaseResponse {

    // Unique identifier for the customer
    private String uuid;

    // First name of the customer (mapped from JSON property "first_name")
    @JsonProperty("first_name")
    private String firstName;

    // Last name of the customer (mapped from JSON property "last_name")
    @JsonProperty("last_name")
    private String lastName;

    // Street address of the customer
    private String street;

    // Full address of the customer
    private String address;

    // City of the customer
    private String city;

    // State of the customer
    private String state;

    // Email address of the customer
    private String email;

    // Phone number of the customer
    private String phone;

    // Default constructor
    public SunBaseResponse() {
        super();
        // TODO Auto-generated constructor stub
    }

    // Parameterized constructor to initialize all fields
    public SunBaseResponse(String uuid, String firstName, String lastName, String street, String address, String city,
                           String state, String email, String phone) {
        super();
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.address = address;
        this.city = city;
        this.state = state;
        this.email = email;
        this.phone = phone;
    }

    // Getter for the UUID field
    public String getUuid() {
        return uuid;
    }

    // Setter for the UUID field
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    // Getter for the first name field
    public String getFirstName() {
        return firstName;
    }

    // Setter for the first name field
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter for the last name field
    public String getLastName() {
        return lastName;
    }

    // Setter for the last name field
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Getter for the street field
    public String getStreet() {
        return street;
    }

    // Setter for the street field
    public void setStreet(String street) {
        this.street = street;
    }

    // Getter for the address field
    public String getAddress() {
        return address;
    }

    // Setter for the address field
    public void setAddress(String address) {
        this.address = address;
    }

    // Getter for the city field
    public String getCity() {
        return city;
    }

    // Setter for the city field
    public void setCity(String city) {
        this.city = city;
    }

    // Getter for the state field
    public String getState() {
        return state;
    }

    // Setter for the state field
    public void setState(String state) {
        this.state = state;
    }

    // Getter for the email field
    public String getEmail() {
        return email;
    }

    // Setter for the email field
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter for the phone field
    public String getPhone() {
        return phone;
    }

    // Setter for the phone field
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Override toString method to return a string representation of the object
    @Override
    public String toString() {
        return "SunBaseResponse [uuid=" + uuid + ", firstName=" + firstName + ", lastName=" + lastName + ", street="
                + street + ", address=" + address + ", city=" + city + ", state=" + state + ", email=" + email
                + ", phone=" + phone + "]";
    }
}
