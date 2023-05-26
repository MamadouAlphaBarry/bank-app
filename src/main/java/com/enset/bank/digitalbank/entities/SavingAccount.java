package com.enset.bank.digitalbank.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.EnableLoadTimeWeaving;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//@DiscriminatorValue("SA")
public class SavingAccount extends  BankAccount {
    private double interestRate;
}
