package com.enset.bank.digitalbank.dao;

import com.enset.bank.digitalbank.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> searchCustomerByNameContains(String keyword);
}
