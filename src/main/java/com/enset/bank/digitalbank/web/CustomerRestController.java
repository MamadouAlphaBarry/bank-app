package com.enset.bank.digitalbank.web;

import com.enset.bank.digitalbank.dtos.CustomerDto;
import com.enset.bank.digitalbank.entities.Customer;
import com.enset.bank.digitalbank.exceptions.CustomerNotFoundException;
import com.enset.bank.digitalbank.service.BankAccountService;
import com.enset.bank.digitalbank.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {
    private BankAccountService bankservice;
    private CustomerService customerService;
   @GetMapping("/api/customers")
    public List<CustomerDto> customers() throws CustomerNotFoundException {
        return customerService.listCustomers();
    }
    @GetMapping("/api/customers/{id}")
    public CustomerDto getCustomer(@PathVariable(name="id") Long customerId) throws CustomerNotFoundException {
    return customerService.getCustomer(customerId);
    }
    @PostMapping("/api/customers")
    public  CustomerDto saveCustomer(Long id, String name, String email){
       CustomerDto customerDto= new CustomerDto();
       customerDto.setId(id);
       customerDto.setName(name);
       customerDto.setEmail(email);
      CustomerDto savedCustomerDto= customerService.saveCustomer(customerDto);
       return  savedCustomerDto;
    }
    @PutMapping("/customers")
    public  CustomerDto updateCustomer(CustomerDto customerDto) throws CustomerNotFoundException {
      return customerService.updateCustomer(customerDto);
    }
    @DeleteMapping("/customers/{id}")
    public String  deleteCustomer(Long id) throws CustomerNotFoundException {
       customerService.deleteCustomer(id);
       return "Le customer a ete supprimer";
    }

   @GetMapping("api/customers/search")
    public  List<CustomerDto> findCustomers(@RequestParam(name = "name",value = "") String keyword){
       return customerService.searchCustomers(keyword);
    }
}
