package com.example.demo.customerServices;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.customerRepository.CustomerRepository;
import com.example.demo.model.CustomerModel;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository; // Repository for interacting with customer data in the database

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository; // Dependency injection of the repository
    }

    /**
     * Save a customer to the database.
     * 
     * @param customer The customer entity to save
     * @return The saved customer entity
     */
    public CustomerModel saveCustomer(CustomerModel customer) {
        return customerRepository.save(customer);
    }

    /**
     * Get all customers from the database.
     * 
     * @return A list of all customer entities
     */
    public List<CustomerModel> getAllCustomer() {
        return customerRepository.findAll(); // Retrieves all customers
    }

    /**
     * Get a customer by their ID.
     * 
     * @param id The ID of the customer
     * @return An Optional containing the customer if found, or empty if not
     */
    public Optional<CustomerModel> getCustomerById(Long id) {
        return customerRepository.findById(id); // Retrieves a customer by their ID
    }

    /**
     * Delete a customer by their ID.
     * 
     * @param id The ID of the customer to delete
     * @return A message indicating success or failure
     */
    public String deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) { // Check if the customer exists
            customerRepository.deleteById(id); // Delete the customer
            return null; // Indicates success
        } else {
            return "Customer not found"; // Indicates failure
        }
    }

    /**
     * Get a paginated list of customers.
     * 
     * @param pageable Pagination information
     * @return A paginated list of customers
     */
    public Page<CustomerModel> getCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable); // Retrieves customers with pagination
    }

    /**
     * Search for customers based on a search query, with pagination.
     * 
     * @param search The search query (e.g., part of a name or email)
     * @param pageable Pagination information
     * @return A paginated list of customers matching the search query
     */
    public Page<CustomerModel> searchCustomers(String search, Pageable pageable) {
        return customerRepository.searchCustomers(search, pageable); // Searches for customers based on the search query
    }
}
