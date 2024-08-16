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
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.example.demo.customerRepository.CustomerInterface;
import com.example.demo.model.CustomerModel;

@Service
public class ExternalApiServices {

    private static final String TOKEN = "dGVzdEBzdW5iYXNlZGF0YS5jb206VGVzdEAxMjM=";
    private static final String API_URL = "https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";

    @Autowired
    private RestTemplate restTemplate;

    private final CustomerInterface customerInterface;

    @Autowired
    public ExternalApiServices(CustomerInterface customerInterface) {
        this.customerInterface = customerInterface;
    }

    public Optional<CustomerModel> getUuid(String uuid) {
        return customerInterface.findByUuid(uuid);
    }

    public List<CustomerModel> getEmployee() {
        HttpHeaders headers = createHeaders(TOKEN);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<CustomerModel>> response = restTemplate.exchange(
                API_URL, HttpMethod.GET, entity, new ParameterizedTypeReference<List<CustomerModel>>() {}
            );
            List<CustomerModel> customers = response.getBody();
            if (customers != null) {
                customers.forEach(this::saveIfNotExists);
            }
            return customers != null ? customers : Collections.emptyList();
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().value() == 403) {
                System.err.println("403 Forbidden: Access denied. Check token or permissions.");
            } else {
                System.err.println("HTTP error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            }
        } catch (Exception e) {
            System.err.println("Error fetching customer list: " + e.getMessage());
        }
        return Collections.emptyList();
    }


    private HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private void saveIfNotExists(CustomerModel customer) {
        customerInterface.findByUuid(customer.getUuid()).ifPresentOrElse(
            existing -> {},
            () -> customerInterface.save(customer)
        );
    }
}
