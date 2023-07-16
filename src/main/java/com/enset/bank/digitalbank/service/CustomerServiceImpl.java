package com.enset.bank.digitalbank.service;

import com.enset.bank.digitalbank.dao.CustomerRepository;
import com.enset.bank.digitalbank.dtos.CustomerDto;
import com.enset.bank.digitalbank.entities.Customer;
import com.enset.bank.digitalbank.exceptions.CustomerNotFoundException;
import com.enset.bank.digitalbank.mappers.CustomerMapperImpl;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements  CustomerService{
    private CustomerRepository customerRepository;
    private CustomerMapperImpl customerMapper;
    @Override
    public CustomerDto getCustomer(Long id) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer Not found"));
        return  customerMapper.fromCustomerToCustomerDto(customer);
    }

    @Override
    public CustomerDto updateCustomer( CustomerDto customerDto) throws CustomerNotFoundException {
        // Customer customer= customerRepository.findById(id).orElseThrow(()->new CustomerNotFoundException("Le customer ayant l'identifiant demandè n'exist pas."));
        Customer customer= customerMapper.fromCustomerDtoToCustomer(customerDto);
        Customer savedCustomer= customerRepository.save(customer);
        return customerMapper.fromCustomerToCustomerDto(savedCustomer);
    }

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        log.info("Saving a new Customer");
        Customer customer= customerMapper.fromCustomerDtoToCustomer(customerDto);
        customerRepository.save(customer);
        return customerMapper.fromCustomerToCustomerDto(customer);
    }

    @Override
    public List<CustomerDto> listCustomers() throws CustomerNotFoundException {
      //  List<CustomerDto> customerDtoList = null;
        List<Customer> customers= customerRepository.findAll();
        List<CustomerDto> customerDtoList = customers.stream().map(customer ->customerMapper.fromCustomerToCustomerDto(customer)).collect(Collectors.toList());
        for (Customer customer:customers) {
            customerDtoList.add(customerMapper.fromCustomerToCustomerDto(customer));
        }
        if (customerDtoList.size()==0) throw new CustomerNotFoundException("Customer vide");
        return customerDtoList;
    }

    @Override
    public void deleteCustomer(Long id) throws CustomerNotFoundException {
        customerRepository.deleteById(id);
    }
}
