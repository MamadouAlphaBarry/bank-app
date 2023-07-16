package com.enset.bank.digitalbank.mappers;

import com.enset.bank.digitalbank.dtos.CustomerDto;
import com.enset.bank.digitalbank.dtos.SavingAccountDto;
import com.enset.bank.digitalbank.entities.Customer;
import com.enset.bank.digitalbank.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapperImpl {
    public CustomerDto fromCustomerToCustomerDto(Customer customer){
        CustomerDto customerDto= new CustomerDto();
        customerDto.setEmail(customer.getEmail());
        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
       // BeanUtils.copyProperties(customer,customerDto);
        return  customerDto;
    }
    public  Customer fromCustomerDtoToCustomer(CustomerDto customerDto){
        Customer customer= new Customer();
       // BeanUtils.copyProperties(customerDto,customer);
        customer.setEmail(customerDto.getEmail());
        customer.setId(customerDto.getId());
        customer.setName(customerDto.getName());
        return  customer;
    }
    public SavingAccountDto fromSavingAccountDtoToSavingAccount(SavingAccount savingAccount){
        SavingAccountDto savingAccountDto= new SavingAccountDto();
        BeanUtils.copyProperties(savingAccount,savingAccountDto);
        savingAccountDto.setCustomerDto(fromCustomerToCustomerDto(savingAccount.getCustomer()));
        savingAccountDto.setType(savingAccount.getClass().getSimpleName());
        return  savingAccountDto;
    }
}
