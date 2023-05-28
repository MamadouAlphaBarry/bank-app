package com.enset.bank.digitalbank.dtos;

import com.enset.bank.digitalbank.entities.BankAccount;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;


@Data
public class CustomerDto {

    private String id;
    private String name;
    private  String email;

     //private List<BankAccount> accounts;
}
