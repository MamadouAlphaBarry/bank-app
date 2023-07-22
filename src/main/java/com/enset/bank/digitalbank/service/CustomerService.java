package com.enset.bank.digitalbank.service;

import com.enset.bank.digitalbank.dtos.CustomerDto;
import com.enset.bank.digitalbank.exceptions.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {
    CustomerDto getCustomer(Long id) throws CustomerNotFoundException;
    CustomerDto updateCustomer(CustomerDto customerDto) throws  CustomerNotFoundException;
    CustomerDto saveCustomer(CustomerDto customerDto);
    List<CustomerDto> listCustomers() throws CustomerNotFoundException;
    void deleteCustomer(Long id) throws  CustomerNotFoundException;
    List<CustomerDto> searchCustomers(String keyword);


}
