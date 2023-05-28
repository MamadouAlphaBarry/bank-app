package com.enset.bank.digitalbank.dtos;

import com.enset.bank.digitalbank.entities.BankAccount;
import com.enset.bank.digitalbank.entities.Customer;
import com.enset.bank.digitalbank.enums.AccountStatus;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data

public class CurrentAccountDto extends BankAccountDto {
    private String id;
    private Date createdAt;
    private  double balance;
    private AccountStatus status;
    private String currency;
    private Customer customer;
    private double interestRate;
    private double overdraft;
}
