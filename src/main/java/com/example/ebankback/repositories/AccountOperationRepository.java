package com.example.ebankback.repositories;

import com.example.ebankback.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
    List<AccountOperation> findAccountOperationById(String accountId);
    //Page<AccountOperation> findAccountsOperationById(String accountId);
}
