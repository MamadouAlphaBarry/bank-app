package com.enset.bank.digitalbank.entities;

import com.enset.bank.digitalbank.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class   BankAccount {
    @Id
    private String id;
    private Date createdAt;
    private  double balance;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private String currency;
    @ManyToOne
    private Customer customer;
    @OneToMany
    private List<Operation> operations;

}
