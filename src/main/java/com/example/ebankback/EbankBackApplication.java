package com.example.ebankback;

import com.example.ebankback.dtos.BankAccountDTO;
import com.example.ebankback.dtos.CustomerDTO;
import com.example.ebankback.entities.*;
import com.example.ebankback.entities.ennum.AccountStatus;
import com.example.ebankback.exceptions.BalanceNotSufficentException;
import com.example.ebankback.exceptions.BankAccountNotFondEception;
import com.example.ebankback.exceptions.CustomerNotFoundException;
import com.example.ebankback.repositories.AccountOperationRepository;
import com.example.ebankback.repositories.BankAccountRepository;
import com.example.ebankback.repositories.CustomerRepository;
import com.example.ebankback.services.BankAccountSerivce;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankBackApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountSerivce bankAccountSerivce){
        return args -> {
            Stream.of("Hassan", "Imane", "Mohamed").forEach(name->{
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setName(name);
                customerDTO.setEmail(name +"@gmail.com");
                bankAccountSerivce.saveCustomer(customerDTO);
            });
            bankAccountSerivce.listCustomer().forEach(cust->{
                try {
                    bankAccountSerivce.saveCurrentBankAccountDTO(Math.random()*90000,9000,cust.getId());
                    bankAccountSerivce.saveSavingBankAccountDTO(Math.random()*120000,5.5,cust.getId());
                    List<BankAccount> bankAccountDTOS = bankAccountSerivce.bankAccountList();
                    for(BankAccount bankAccount:bankAccountDTOS){
                        for (int i = 0; i<10 ; i++){
                            bankAccountSerivce.credit(bankAccount.getId(), 10000+Math.random()*120000,"Crédit");
                            bankAccountSerivce.debit(bankAccount.getId(),1000+Math.random()*9000,"Debit");
                        }
                    }
                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                } catch (BankAccountNotFondEception | BalanceNotSufficentException e) {
                    throw new RuntimeException(e);
                }
            });

        };
    }

    //@Bean
    CommandLineRunner start(BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository,
                            CustomerRepository customerRepository){
        return args ->{
            Stream.of("saad","yassmine").forEach(name->{
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });

            customerRepository.findAll().forEach(cust->{
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*9000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);
            });

            customerRepository.findAll().forEach(cust->{
                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*5000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.ACTIVATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterstRate(5000);
                bankAccountRepository.save(savingAccount);
            });
        };
    }
}
