package com.example.ebankback.dtos;

import com.example.ebankback.entities.AccountOperation;
import com.example.ebankback.entities.Customer;
import com.example.ebankback.entities.ennum.AccountStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SavingBankAccountDTO extends BankAccountDTO {
    private String Id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double interestRaye;

}
