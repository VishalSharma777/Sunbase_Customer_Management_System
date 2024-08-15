package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.customerServices.CustomerService;
import com.example.demo.customerServices.SunBaseData;
import com.example.demo.model.CustomerModel;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    // Injecting CustomerService and SunBaseData services
    private final CustomerService customerService;
    private final SunBaseData sunbaseData;

    @Autowired
    public CustomerController(CustomerService customerService, SunBaseData sunbaseData) {
        this.customerService = customerService;
        this.sunbaseData = sunbaseData;
    }

    // Test endpoint for checking API connectivity
    @GetMapping
    public String test() {
        return "test string";
    }

    // Create a new customer (Public endpoint)
    @PostMapping("/public/create")
    public CustomerModel createCustomer(@RequestBody CustomerModel customer) {
        return customerService.saveCustomer(customer);
    }

    // Retrieve a customer by ID (Public endpoint)
    @GetMapping("/public/{id}")
    public ResponseEntity<CustomerModel> getCustomerById(@PathVariable Long id) {
        Optional<CustomerModel> customer = customerService.getCustomerById(id);
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update an existing customer by ID (Public endpoint)
    @PutMapping("/public/{id}")
    public ResponseEntity<CustomerModel> updateCustomer(@PathVariable Long id, @RequestBody CustomerModel customerDetails) {
        Optional<CustomerModel> customer = customerService.getCustomerById(id);
        if (customer.isPresent()) {
            // Update fields that are not null in the provided customerDetails object
            CustomerModel customerToUpdate = customer.get();
            if (customerDetails.getFirstName() != null) customerToUpdate.setFirstName(customerDetails.getFirstName());
            if (customerDetails.getLastName() != null) customerToUpdate.setLastName(customerDetails.getLastName());
            if (customerDetails.getStreet() != null) customerToUpdate.setStreet(customerDetails.getStreet());
            if (customerDetails.getCity() != null) customerToUpdate.setCity(customerDetails.getCity());
            if (customerDetails.getAddress() != null) customerToUpdate.setAddress(customerDetails.getAddress());
            if (customerDetails.getEmail() != null) customerToUpdate.setEmail(customerDetails.getEmail());
            if (customerDetails.getPhone() != null) customerToUpdate.setPhone(customerDetails.getPhone());
            if (customerDetails.getState() != null) customerToUpdate.setState(customerDetails.getState());

            // Save the updated customer
            CustomerModel updatedCustomer = customerService.saveCustomer(customerToUpdate);
            return ResponseEntity.ok(updatedCustomer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a customer by ID (Public endpoint)
    @DeleteMapping("/public/{id}")
    public ResponseEntity<CustomerModel> deleteCustomer(@PathVariable Long id) {
        String check = customerService.deleteCustomer(id);
        if (check != null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    // Get a list of all customers (Public endpoint)
    @GetMapping("/public/getAll")
    public ResponseEntity<List<CustomerModel>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomer());
    }

    // Get paginated and sorted list of customers with optional search (Public endpoint)
    @GetMapping("/public/customerList")
    public Page<CustomerModel> getCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(required = false) String search) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        if (search != null && !search.isEmpty()) {
            // If search parameter is provided, search customers
            return customerService.searchCustomers(search, pageable);
        } else {
            // If no search parameter, return all customers with pagination and sorting
            return customerService.getCustomers(pageable);
        }
    }

    // Fetch a list of users from an external source (SunBaseData) (Public endpoint)
    @GetMapping("/public/usersList")
    public ResponseEntity<List<CustomerModel>> getAllUser() {
        List<CustomerModel> data = sunbaseData.getUserList();
        if (data == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(data);
    }
}
