package com.enset.bank.digitalbank.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private  String email;
    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)
     private List<BankAccount> accounts;
}
