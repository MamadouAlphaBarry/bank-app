package com.enset.bank.digitalbank.dtos;

import com.enset.bank.digitalbank.enums.OperationType;

import java.util.Date;

public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;

}
