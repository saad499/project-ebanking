package com.example.ebankback;

import com.example.ebankback.entities.AccountOperation;
import com.example.ebankback.entities.CurrentAccount;
import com.example.ebankback.entities.Customer;
import com.example.ebankback.entities.SavingAccount;
import com.example.ebankback.entities.ennum.AccountStatus;
import com.example.ebankback.repositories.AccountOperationRepository;
import com.example.ebankback.repositories.BankAccountRepository;
import com.example.ebankback.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankBackApplication.class, args);
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
