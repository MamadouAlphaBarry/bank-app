package com.enset.bank.digitalbank.dao;

import com.enset.bank.digitalbank.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {

    List<AccountOperation> findOperationsById(Long id);

    Page<AccountOperation> findOperationsByAccount_Id(String accountId, Pageable pageable);
   // List<AccountOperation> findByAccountId(String accountId);
    Page<AccountOperation> findByAccountIdOrderByDateDesc(String accountId, Pageable pageable);

    //Page<AccountOperation> findAccountIdOrderByDateDesc(String accountId, PageRequest of);
}
