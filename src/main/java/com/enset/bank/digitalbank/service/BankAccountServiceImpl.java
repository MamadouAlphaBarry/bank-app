package com.enset.bank.digitalbank.service;

import com.enset.bank.digitalbank.dao.AccountOperationRepository;
import com.enset.bank.digitalbank.dao.BankAccountReposittory;
import com.enset.bank.digitalbank.dao.CustomerRepository;
import com.enset.bank.digitalbank.entities.*;
import com.enset.bank.digitalbank.enums.AccountStatus;
import com.enset.bank.digitalbank.enums.OperationType;
import com.enset.bank.digitalbank.exceptions.BalanceNotSufficientException;
import com.enset.bank.digitalbank.exceptions.BankAccountNotFoundException;
import com.enset.bank.digitalbank.exceptions.CustomerNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    CustomerRepository customerRepository;
    BankAccountReposittory bankAccountReposittory;
    AccountOperationRepository accountOperationRepository;

    @Override
    public Customer saveCustomer(Customer customer) {
        log.info("Saving Customer");
        Customer customer1= customerRepository.save(customer);
        return customer1;
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, double overdraft, Long customerId) throws CustomerNotFoundException {
        Customer customer= customerRepository.findById(customerId).orElse(null);
        if (customer==null){
          throw new CustomerNotFoundException("Le customer recherchern'existe pas.");
        }
      CurrentAccount  bankAccount= new CurrentAccount();
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setBalance(initialBalance);
        bankAccount.setCustomer(customer);
        bankAccount.setStatus(AccountStatus.CREATED);
        bankAccount.setOverdraft(overdraft);
        bankAccount.setCurrency("euro");
        bankAccount.setCreatedAt(new Date());
        bankAccountReposittory.save(bankAccount);
        return bankAccount;
    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, double interest, Long customerId) throws CustomerNotFoundException {
        Customer customer= customerRepository.findById(customerId).orElse(null);
        if (customer==null){
            throw new CustomerNotFoundException("Le customer recherchern'existe pas.");
        }
        SavingAccount  bankAccount= new SavingAccount();
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setBalance(initialBalance);
        bankAccount.setCustomer(customer);
        bankAccount.setStatus(AccountStatus.CREATED);
        bankAccount.setCurrency("euro");
        bankAccount.setCreatedAt(new Date());
        bankAccount.setInterestRate(interest);
        bankAccountReposittory.save(bankAccount);
        return bankAccount;
    }


    @Override
    public List<Customer> listCustomers() {
        log.info("************************Liste des Clients de la Banque******************************");
        return customerRepository.findAll();
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount= bankAccountReposittory.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("Le Compte bancaire n'existe pas.")) ;

        return null;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount= getBankAccount(accountId);
        if (amount>bankAccount.getBalance()){
            throw new BalanceNotSufficientException("Solde insuffisant");
        }
        log.info("************************Operation de debit sur le compte***************************");
        AccountOperation accountOperation1= new AccountOperation();
        accountOperation1.setAccount(bankAccount);
        accountOperation1.setType(OperationType.DEBIT);
        accountOperation1.setDate(new Date());
        accountOperation1.setDescription("Operation de retraite");
        accountOperationRepository.save(accountOperation1);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountReposittory.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        log.info("****************operation de Credit sur le compte****************************");
        BankAccount bankAccount= getBankAccount(accountId);
        AccountOperation accountOperation1= new AccountOperation();
        accountOperation1.setAccount(bankAccount);
        accountOperation1.setType(OperationType.CREDIT);
        accountOperation1.setDate(new Date());
        accountOperation1.setDescription("Operation de credit sur le compte "+bankAccount.getId());
        accountOperationRepository.save(accountOperation1);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountReposittory.save(bankAccount);
    }

    @Override
    public void transfert(String accountIdsource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccountSource= bankAccountReposittory.findById(accountIdsource).orElseThrow(()->new BankAccountNotFoundException("le compte source n'a pas ete trouve"));
        BankAccount bankAccountDestination= bankAccountReposittory.findById(accountIdDestination).orElseThrow(()->new BankAccountNotFoundException("Le compte destination n'existe pas."));
        if (amount>bankAccountSource.getBalance()){
            throw new BalanceNotSufficientException("Le montant demandè est trop grand");
        }
        bankAccountDestination.setBalance(amount);
    }

    @Override
    public List<BankAccount> bankAccountlist() {
        log.info("************************Liste des compte bancaire************************************");
        return bankAccountReposittory.findAll();
    }
}
