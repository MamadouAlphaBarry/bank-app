package com.enset.bank.digitalbank.dao;

import com.enset.bank.digitalbank.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountReposittory extends JpaRepository<BankAccount,String> {
}
