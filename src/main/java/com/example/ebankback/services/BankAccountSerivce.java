package com.example.ebankback.services;

import com.example.ebankback.dtos.*;
import com.example.ebankback.entities.AccountOperation;

import java.util.List;

public interface BankAccountSerivce {

    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentBankAccountDTO saveCurrentBankAccountDTO(double initialBalance, double overDraft, Long customerId);
    SavingBankAccountDTO saveSavingBankAccountDTO(double initialBalance, double interestRaye, Long customerId);
    List<CustomerDTO> listCustomer();
    BankAccountDTO getBankAccount(String accountId);
    void debit(String accountId, double amount, String description);
    void credit(String accountId, double amount, String description);
    void transfert(String accountIdSource, String accountIdDestination, double amount);
    List<BankAccountDTO> bankAccountList();
    CustomerDTO getCustomer(Long customerId);
    CustomerDTO updateCustomer(CustomerDTO customerDTO);
    CustomerDTO deleteCustomer(Long customerId);
    List<AccountOperationDTO>accountHistory(String accountId);
    AccountOperation getAccountHistory(String accountId, int page, int size);
    List<CustomerDTO> search(String keyword);
}
