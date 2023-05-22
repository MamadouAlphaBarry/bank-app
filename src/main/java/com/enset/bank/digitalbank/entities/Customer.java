package com.enset.bank.digitalbank.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Customer {
    @Id
    private String id;
    private String name;
    private  String email;
    @OneToMany
     private List<BankAccount> accounts;
}
