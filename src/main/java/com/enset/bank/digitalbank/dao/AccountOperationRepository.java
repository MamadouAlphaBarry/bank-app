package com.enset.bank.digitalbank.dao;

import com.enset.bank.digitalbank.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<Operation, Long> {

    List<Operation> findOperationsById(Long id);

    Page<Operation> findOperationsByAccount_Id(String accountId, Pageable pageable);
}
