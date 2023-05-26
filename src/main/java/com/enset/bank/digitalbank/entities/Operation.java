package com.enset.bank.digitalbank.entities;

import com.enset.bank.digitalbank.enums.OPerationType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data

public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private Date date;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OPerationType type;
    @ManyToOne
    private BankAccount account;
}
