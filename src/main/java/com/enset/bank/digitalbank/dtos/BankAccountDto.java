package com.enset.bank.digitalbank.dtos;

import com.enset.bank.digitalbank.entities.Customer;
import com.enset.bank.digitalbank.entities.Operation;
import com.enset.bank.digitalbank.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data

public abstract class BankAccountDto {
    private  String type;


}
