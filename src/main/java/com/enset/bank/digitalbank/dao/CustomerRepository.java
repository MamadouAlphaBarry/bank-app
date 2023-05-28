package com.enset.bank.digitalbank.dao;

import com.enset.bank.digitalbank.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
