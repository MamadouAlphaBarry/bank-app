package com.enset.bank.digitalbank.entities;

import com.enset.bank.digitalbank.enums.OperationType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data

public class AccountOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private Date date;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    @ManyToOne
    private BankAccount account;
    private String description;
}
