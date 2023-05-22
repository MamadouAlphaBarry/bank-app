package com.enset.bank.digitalbank.entities;

import com.enset.bank.digitalbank.enums.OPerationType;
import jakarta.persistence.ManyToOne;

import java.util.Date;

public class Operation {
    private  Long id;
    private Date date;
    private double ampunt;
    private OPerationType type;
    @ManyToOne
    private BankAccount account;
}
