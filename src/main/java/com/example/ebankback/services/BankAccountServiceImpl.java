package com.example.ebankback.services;

import com.example.ebankback.dtos.*;
import com.example.ebankback.entities.*;
import com.example.ebankback.entities.ennum.OperationType;
import com.example.ebankback.mappers.BankAccountMapperImpl;
import com.example.ebankback.repositories.AccountOperationRepository;
import com.example.ebankback.repositories.BankAccountRepository;
import com.example.ebankback.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class BankAccountServiceImpl implements BankAccountSerivce{

    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl dtoMapper;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccountDTO(double initialBalance, double overDraft, Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        CurrentAccount savedBankAccount = bankAccountRepository.save(currentAccount);
        return dtoMapper.fromCurrentBankAccount(savedBankAccount);
    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccountDTO(double initialBalance, double interestRaye, Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterstRate(interestRaye);
        savingAccount.setCustomer(customer);
        SavingAccount savedBankAccount = bankAccountRepository.save(savingAccount);
        return dtoMapper.fromSavingBankAccount(savedBankAccount);
    }

    @Override
    public List<CustomerDTO> listCustomer() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> collect = customers.stream()
                .map(customer -> dtoMapper.fromCustomer(customer))
                .collect(Collectors.toList());
        /*List<CustomerDTO> customerDTOS = new ArrayList<>();
        for (Customer customer: customers){
            CustomerDTO customerDTO=dtoMapper.fromCustomer(customer);
            customerDTOS.add(customerDTO);
        }*/

        return collect;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElse(null);
        if(bankAccount instanceof SavingAccount){
            SavingAccount savingAccount = (SavingAccount) bankAccount;
            return dtoMapper.fromSavingBankAccount(savingAccount);
        }else{
            CurrentAccount currentAccount = (CurrentAccount) bankAccount;
            return dtoMapper.fromCurrentBankAccount(currentAccount);
        }
    }

    @Override
    public void debit(String accountId, double amount, String description) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElse(null);
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setBankAccounts(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElse(null);
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setBankAccounts(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfert(String accountIdSource, String accountIdDestination, double amount) {
        debit(accountIdSource, amount, "Transfert to "+accountIdDestination);
        credit(accountIdDestination,amount,"Treansfert from "+accountIdSource);
    }

    @Override
    public List<BankAccountDTO> bankAccountList() {
        return null;
    }

    @Override
    public CustomerDTO getCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        return dtoMapper.fromCustomer(customer);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CustomerDTO deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
        return null;
    }

    @Override
    public List<AccountOperationDTO> accountHistory(String accountId) {
       // List<AccountOperation> accountOperations = (List<AccountOperation>) accountOperationRepository.findAccountOperationById(accountId);

        //return accountOperations.stream().map(op->dtoMapper.from);
        return null;
    }

    @Override
    public AccountOperation getAccountHistory(String accountId, int page, int size) {
        return null;
    }

    @Override
    public List<CustomerDTO> search(String keyword) {
        List<Customer> customers = customerRepository.findByNameContains(keyword);
        List<CustomerDTO> customerDTOS = customers.stream().map(cust->dtoMapper.fromCustomer(cust)).collect(Collectors.toList());
        return customerDTOS;
    }
}
