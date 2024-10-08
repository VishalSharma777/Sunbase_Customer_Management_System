package com.example.demo.customerRepository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
 
import com.example.demo.model.UserModel;

public interface UserInterface extends CrudRepository<UserModel, Long> {

    /**
     * Custom method to find a user by their email address.
     * 
     * @param email The email address of the user.
     * @return An Optional containing the user if found, or empty if not.
     */
    Optional<UserModel> findByEmail(String email);
}
