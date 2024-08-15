package com.example.demo.customerServices;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.customerRepository.CustomerRepository;
import com.example.demo.model.CustomerModel;

@Service
public class SunBaseData {

    // Static token and API URL for accessing the external service
    private static String token = "dGVzdEBzdW5iYXNlZGF0YS5jb206VGVzdEAxMjM=";
    private static String API = "https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";
    
    // Autowired RestTemplate for making HTTP requests
    @Autowired
    private RestTemplate restTemplate;
    
    // Dependency injected CustomerRepository
    private final CustomerRepository customerRepository;
    
    // Constructor for injecting CustomerRepository
    @Autowired
    public SunBaseData(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Method to get a customer by UUID from the local database
    public Optional<CustomerModel> getUuid(String uuid) {
        return customerRepository.findByUuid(uuid);
    }
    
    // Method to get a list of customers from an external API
    public List<CustomerModel> getUserList() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token); // Set the Authorization header
        headers.setContentType(MediaType.APPLICATION_JSON); // Set content type to JSON
        HttpEntity<String> entity = new HttpEntity<>(headers); // Create HttpEntity with headers

        try {
            // Make GET request to the external API
            ResponseEntity<List<CustomerModel>> response = restTemplate.exchange(
                API,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<CustomerModel>>() {}
            );

            // Check if the response status is 2xx (successful)
            if (response.getStatusCode().is2xxSuccessful()) {
                List<CustomerModel> customers = response.getBody(); // Get the response body (list of customers)
                if (customers != null) {
                    // Iterate through the list of customers
                    for (CustomerModel customer : customers) {
                        // Check if the customer already exists in the local database
                        Optional<CustomerModel> existingCustomer = customerRepository.findByUuid(customer.getUuid());
                        if (existingCustomer.isEmpty()) {
                            // Save the customer if not already present
                            customerRepository.save(customer);
                        }
                    }
                }
                return customers; // Return the list of customers
            } else {
                // Log the response status code if not successful
                System.out.println("Response status code: " + response.getStatusCode());
                return Collections.emptyList(); // Return an empty list if the request was not successful
            }
        } catch (Exception e) {
            // Print stack trace if an exception occurs
            e.printStackTrace();
            return Collections.emptyList(); // Return an empty list if an exception occurs
        }
    }

    // Example of a method that was commented out (to be implemented)
    // private CustomerModel customerConverted(SunBaseResponse data1) {
    //     // TODO Auto-generated method stub
    //     CustomerModel cust = new CustomerModel();
    //     cust.setFirstName(data1.getFirstName());
    //     cust.setLastName(data1.getLastName());
    //     cust.setEmail(data1.getEmail());
    //     cust.setAddress(data1.getAddress());
    //     cust.setCity(data1.getCity());
    //     cust.setPhone(data1.getPhone());
    //     cust.setState(data1.getState());
    //     cust.setStreet(data1.getStreet());
    //     return cust;
    // }
}
