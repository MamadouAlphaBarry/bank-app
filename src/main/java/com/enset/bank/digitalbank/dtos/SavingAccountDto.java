package com.enset.bank.digitalbank.dtos;

import com.enset.bank.digitalbank.enums.AccountStatus;
import lombok.Data;

import java.util.Date;


@Data


public class SavingAccountDto extends BankAccountDto {
    private String id;
    private Date createdAt;
    private  double balance;
    private AccountStatus status;
    private String currency;
    private CustomerDto customerDto;
    private double interestRate;
}
