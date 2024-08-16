package com.example.demo.customerRepository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.CustomerModel;

public interface CustomerInterface extends JpaRepository<CustomerModel, Long> {

    /**
     * Custom search query to find customers by first name, email, or city.
     * The query performs a case-insensitive search using the `LIKE` operator.
     * 
     * @param search The search string to match against first name, email, or city.
     * @param pageable The pagination and sorting information.
     * @return A paginated list of customers matching the search criteria.
     */
    @Query("SELECT c FROM CustomerModel c WHERE " +
           "LOWER(c.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.city) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<CustomerModel> searchCustomers(@Param("search") String search, Pageable pageable);

    /**
     * Finds a customer by their UUID.
     * 
     * @param uuid The UUID of the customer.
     * @return An Optional containing the customer if found, or empty if not.
     */
    Optional<CustomerModel> findByUuid(String uuid);
}
