package com.enset.bank.digitalbank.service;

import com.enset.bank.digitalbank.dao.AccountOperationRepository;
import com.enset.bank.digitalbank.dao.BankAccountReposittory;
import com.enset.bank.digitalbank.dao.CustomerRepository;
import com.enset.bank.digitalbank.dtos.*;
import com.enset.bank.digitalbank.entities.*;
import com.enset.bank.digitalbank.enums.AccountStatus;
import com.enset.bank.digitalbank.enums.OperationType;
import com.enset.bank.digitalbank.exceptions.BalanceNotSufficientException;
import com.enset.bank.digitalbank.exceptions.BankAccountNotFoundException;
import com.enset.bank.digitalbank.exceptions.CustomerNotFoundException;
import com.enset.bank.digitalbank.mappers.BankAccountMapperImpl;
import com.enset.bank.digitalbank.mappers.CustomerMapperImpl;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    CustomerRepository customerRepository;
    BankAccountReposittory bankAccountReposittory;
    AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl bankAccountMapper;
    private CustomerMapperImpl customerMapper;


    @Override
    public CurrentAccountDto saveCurrentBankAccount(double initialBalance, double overdraft, Long customerId) throws CustomerNotFoundException {
        Customer customer= customerRepository.findById(customerId).orElse(null);
        if (customer==null){
          throw new CustomerNotFoundException("Le customer recherchern'existe pas.");
        }
      CurrentAccountDto  bankAccount= new CurrentAccountDto();
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setBalance(initialBalance);
        bankAccount.setCustomerDto(bankAccountMapper.fromCustomerToCustomerDto(customer));
        bankAccount.setStatus(AccountStatus.CREATED);
        bankAccount.setOverdraft(overdraft);
        bankAccount.setCurrency("euro");
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setCreatedAt(new Date());
        BankAccount bank= bankAccountMapper.fromCurrentAccountDtoToCurrentAccount(bankAccount);


      bankAccountReposittory.save(bank);
        return bankAccount;
    }

    @Override
    public SavingAccountDto saveSavingBankAccount(double initialBalance, double interest, Long customerId) throws CustomerNotFoundException {
        Customer customer= customerRepository.findById(customerId).orElse(null);
        if (customer==null){
            throw new CustomerNotFoundException("Le customer recherchern'existe pas.");
        }

        SavingAccountDto bankAccount= new SavingAccountDto();
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setBalance(initialBalance);
        bankAccount.setCustomerDto(customerMapper.fromCustomerToCustomerDto(customer));
        bankAccount.setStatus(AccountStatus.CREATED);
        bankAccount.setCurrency("euro");
        bankAccount.setCreatedAt(new Date());
        bankAccount.setInterestRate(interest);
        bankAccountReposittory.save(bankAccountMapper.fromSavingAccountTOSavingAccountDto(bankAccount));
        return bankAccount;
    }



    @Override
    public BankAccountDto getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount= bankAccountReposittory.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("Le Compte bancaire n'existe pas.")) ;
    if (bankAccount instanceof CurrentAccount){
        return  bankAccountMapper.fromCurrentAccountToCurrentAccountDto((CurrentAccount) bankAccount);
    }else{
        SavingAccount savingAccount= (SavingAccount) bankAccount;
        return  bankAccountMapper.fromSavingAccountDtoToSavingAccount(savingAccount);
    }

    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount=bankAccountReposittory.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        if(bankAccount.getBalance()<amount)
            throw new BalanceNotSufficientException("Balance not sufficient");
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setDate(new Date());
        accountOperation.setAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountReposittory.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountReposittory.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setDate(new Date());
        accountOperation.setAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountReposittory.save(bankAccount);
    }

    @Override
    public void transfert(String accountIdsource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
       /* BankAccount bankAccountSource= bankAccountReposittory.findById(accountIdsource).orElseThrow(()->new BankAccountNotFoundException("le compte source n'a pas ete trouve"));
        BankAccount bankAccountDestination= bankAccountReposittory.findById(accountIdDestination).orElseThrow(()->new BankAccountNotFoundException("Le compte destination n'existe pas."));
        if (amount>bankAccountSource.getBalance()){
            throw new BalanceNotSufficientException("Le montant demandè est trop grand");
        }
        bankAccountDestination.setBalance(amount);*/
        credit(accountIdDestination,amount,"Transfert From"+accountIdsource);
        debit(accountIdsource,amount,"Transfert to "+ accountIdDestination);

    }

    @Override
    public List<BankAccountDto> bankAccountList() {
        log.info("************************Liste des compte bancaire************************************");
        List<BankAccount> bankAccounts = bankAccountReposittory.findAll();
        List<BankAccountDto> bankAccountDTOS = bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof SavingAccount) {
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return bankAccountMapper.fromSavingAccountDtoToSavingAccount(savingAccount);
            } else {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return bankAccountMapper.fromCurrentAccountToCurrentAccountDto(currentAccount);
            }
        }).collect(Collectors.toList());
        return bankAccountDTOS;
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
       /* BankAccount bankAccount=bankAccountReposittory.findById(accountId).orElse(null);
        if(bankAccount==null) throw new BankAccountNotFoundException("Account not Found");
        Page<AccountOperation> accountOperations = accountOperationRepository.findAccountIdOrderByOperationDateDesc(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op -> bankAccountMapper.fromAccountOperationToOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;*/
        return  null;
    }
    @Override
    public List<AccountOperationDTO> accountHistory(String accountId){
        //List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId);
      //  return accountOperations.stream().map(op->bankAccountMapper.fromAccountOperationToOperation(op)).collect(Collectors.toList());
    return null;}


}
