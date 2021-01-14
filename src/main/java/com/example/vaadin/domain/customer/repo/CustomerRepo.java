package com.example.vaadin.domain.customer.repo;

import com.example.vaadin.domain.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CustomerRepo extends JpaRepository<Customer, UUID> {

    List<Customer> findByLastNameStartsWithIgnoreCase(String lastName);
}
