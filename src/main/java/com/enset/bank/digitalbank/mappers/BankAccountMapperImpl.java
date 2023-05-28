package com.enset.bank.digitalbank.mappers;

import com.enset.bank.digitalbank.dtos.AccountOperationDTO;
import com.enset.bank.digitalbank.dtos.CurrentAccountDto;
import com.enset.bank.digitalbank.dtos.CustomerDto;
import com.enset.bank.digitalbank.dtos.SavingAccountDto;
import com.enset.bank.digitalbank.entities.CurrentAccount;
import com.enset.bank.digitalbank.entities.Customer;
import com.enset.bank.digitalbank.entities.AccountOperation;
import com.enset.bank.digitalbank.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {
    public CustomerDto fromCustomerToCustomerDto(Customer customer){
        CustomerDto customerDto= new CustomerDto();
        BeanUtils.copyProperties(customer,customerDto);
        return  customerDto;
    }
    public  Customer fromCustomerDtoToCustomer(CustomerDto customerDto){
      Customer customer= new Customer();
      BeanUtils.copyProperties(customerDto,customer);
      return  customer;
    }
    public SavingAccountDto fromSavingAccountDtoToSavingAccount(SavingAccount savingAccount){
        SavingAccountDto savingAccountDto= new SavingAccountDto();
        BeanUtils.copyProperties(savingAccount,savingAccountDto);
        savingAccountDto.setCustomerDto(fromCustomerToCustomerDto(savingAccount.getCustomer()));
        savingAccountDto.setType(savingAccount.getClass().getSimpleName());
        return  savingAccountDto;
    }
    public SavingAccount fromSavingAccountTOSavingAccountDto(SavingAccountDto savingAccountDto){
        SavingAccount savingAccount= new SavingAccount();
        BeanUtils.copyProperties(savingAccountDto,savingAccount);
        return  savingAccount;
    }

    public CurrentAccountDto fromCurrentAccountToCurrentAccountDto(CurrentAccount currentAccount){
        CurrentAccountDto currentAccountDto= new CurrentAccountDto();
        BeanUtils.copyProperties(currentAccount, currentAccountDto);
        currentAccountDto.setCustomerDto(fromCustomerToCustomerDto(currentAccount.getCustomer()));
        currentAccountDto.setType(currentAccount.getClass().getSimpleName());
        return currentAccountDto;
    }
    public AccountOperationDTO fromAccountOperationToOperation(AccountOperation operation){
        AccountOperationDTO accountOperationDTO= new AccountOperationDTO();
        BeanUtils.copyProperties(operation,accountOperationDTO);
        return accountOperationDTO;
    }
}
