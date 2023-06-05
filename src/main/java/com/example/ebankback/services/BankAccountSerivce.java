package com.example.ebankback.services;

import com.example.ebankback.dtos.*;
import com.example.ebankback.entities.AccountOperation;
import com.example.ebankback.entities.BankAccount;
import com.example.ebankback.exceptions.BalanceNotSufficentException;
import com.example.ebankback.exceptions.BankAccountNotFondEception;
import com.example.ebankback.exceptions.CustomerNotFoundException;
import com.example.ebankback.exceptions.IllegalArgumentException;

import java.util.List;

public interface BankAccountSerivce {

    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentBankAccountDTO saveCurrentBankAccountDTO(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccountDTO(double initialBalance, double interestRaye, Long customerId) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomer();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFondEception;
    void debit(String accountId, double amount, String description) throws BalanceNotSufficentException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFondEception;
    void transfert(String accountIdSource, String accountIdDestination, double amount) throws BalanceNotSufficentException, BankAccountNotFondEception;
    List<BankAccount> bankAccountList();
    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException, IllegalArgumentException;
    CustomerDTO updateCustomer(CustomerDTO customerDTO) throws CustomerNotFoundException, IllegalArgumentException;
    CustomerDTO deleteCustomer(Long customerId) throws CustomerNotFoundException, IllegalArgumentException;
    List<AccountOperationDTO>accountHistory(String accountId);
    AccountOperation getAccountHistory(String accountId, int page, int size);
    List<CustomerDTO> search(String keyword);
}
