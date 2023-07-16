package com.enset.bank.digitalbank.mappers;

import com.enset.bank.digitalbank.dtos.AccountOperationDTO;
import com.enset.bank.digitalbank.dtos.CurrentAccountDto;
import com.enset.bank.digitalbank.dtos.CustomerDto;
import com.enset.bank.digitalbank.dtos.SavingAccountDto;
import com.enset.bank.digitalbank.entities.CurrentAccount;
import com.enset.bank.digitalbank.entities.Customer;
import com.enset.bank.digitalbank.entities.AccountOperation;
import com.enset.bank.digitalbank.entities.SavingAccount;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BankAccountMapperImpl {
    public CustomerDto fromCustomerToCustomerDto(Customer customer){
        CustomerDto customerDto= new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        customerDto.setEmail(customerDto.getEmail());
       // BeanUtils.copyProperties(customerDto,customer);
        return  customerDto;
    }
    public  Customer fromCustomerDtoToCustomer(CustomerDto customerDto){
      Customer customer= new Customer();
      customerDto.setId(customer.getId());
      customerDto.setName(customer.getName());
      customerDto.setEmail(customer.getEmail());
     // BeanUtils.copyProperties(customerDto,customer);
      return  customer;
    }
    public SavingAccountDto fromSavingAccountDtoToSavingAccount(SavingAccount savingAccount){
        SavingAccountDto savingAccountDto= new SavingAccountDto();
       // BeanUtils.copyProperties(savingAccountDto,savingAccount);
        savingAccountDto.setId(savingAccount.getId());
        savingAccountDto.setCurrency(savingAccount.getCurrency());
        savingAccountDto.setBalance(savingAccount.getBalance());
        savingAccountDto.setCreatedAt(savingAccount.getCreatedAt());
        savingAccountDto.setInterestRate(savingAccount.getInterestRate());
        savingAccountDto.setCustomerDto(fromCustomerToCustomerDto(savingAccount.getCustomer()));
        savingAccountDto.setType(savingAccount.getClass().getSimpleName());
        return  savingAccountDto;
    }
    public SavingAccount fromSavingAccountTOSavingAccountDto(SavingAccountDto savingAccountDto){
        SavingAccount savingAccount= new SavingAccount();
        savingAccount.setInterestRate(savingAccountDto.getInterestRate());
        savingAccount.setBalance(savingAccountDto.getBalance());
        savingAccount.setId(savingAccountDto.getId());
        savingAccount.setCurrency(savingAccountDto.getCurrency());
        savingAccount.setCreatedAt(savingAccountDto.getCreatedAt());
        savingAccount.setCustomer(fromCustomerDtoToCustomer(savingAccountDto.getCustomerDto()));
        //BeanUtils.copyProperties(savingAccountDto,savingAccount);
        return  savingAccount;
    }

    public CurrentAccountDto fromCurrentAccountToCurrentAccountDto(CurrentAccount currentAccount){
        CurrentAccountDto currentAccountDto= new CurrentAccountDto();

       // BeanUtils.copyProperties(currentAccount, currentAccountDto);
        currentAccountDto.setId(currentAccount.getId());
        //currentAccountDto.setType("current");
        currentAccountDto.setBalance(currentAccount.getBalance());
        currentAccountDto.setCurrency(currentAccount.getCurrency());
        currentAccountDto.setOverdraft(currentAccountDto.getOverdraft());
        currentAccountDto.setStatus(currentAccount.getStatus());

        currentAccountDto.setCustomerDto(fromCustomerToCustomerDto(currentAccount.getCustomer()));
        currentAccountDto.setType(currentAccount.getClass().getSimpleName());
        return currentAccountDto;
    }
    public CurrentAccount fromCurrentAccountDtoToCurrentAccount(CurrentAccountDto currentAccountDto){
        CurrentAccount currentAccount= new CurrentAccount();
        BeanUtils.copyProperties(currentAccountDto,currentAccount);
       currentAccount.setCustomer(fromCustomerDtoToCustomer(currentAccountDto.getCustomerDto()));
        currentAccountDto.setType(currentAccount.getClass().getSimpleName());
        return currentAccount;
    }
    public AccountOperationDTO fromAccountOperationToOperation(AccountOperation operation){
        AccountOperationDTO accountOperationDTO= new AccountOperationDTO();
        accountOperationDTO.setType(operation.getType());
        accountOperationDTO.setOperationDate(operation.getDate());
        accountOperationDTO.setId(operation.getId());
        accountOperationDTO.setAmount(operation.getAmount());
        accountOperationDTO.setDescription(operation.getDescription());
        BeanUtils.copyProperties(operation,accountOperationDTO);
        return accountOperationDTO;
    }
}
