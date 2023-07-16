package com.enset.bank.digitalbank.service;

import com.enset.bank.digitalbank.dtos.*;
import com.enset.bank.digitalbank.entities.BankAccount;
import com.enset.bank.digitalbank.entities.CurrentAccount;
import com.enset.bank.digitalbank.entities.Customer;
import com.enset.bank.digitalbank.entities.SavingAccount;
import com.enset.bank.digitalbank.exceptions.BalanceNotSufficientException;
import com.enset.bank.digitalbank.exceptions.BankAccountNotFoundException;
import com.enset.bank.digitalbank.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {

    CurrentAccountDto saveCurrentBankAccount(double initialBalance, double overdraft, Long customerId) throws CustomerNotFoundException;
    SavingAccountDto saveSavingBankAccount(double initialBalance, double interest, Long customerId) throws CustomerNotFoundException;

    BankAccountDto getBankAccount(String accountId)throws BankAccountNotFoundException;
    void debit(String accountId, double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId,double amount,String description) throws BankAccountNotFoundException;
    void  transfert(String accountIdsource,String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

    List<BankAccountDto> bankAccountList();
    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;





}
