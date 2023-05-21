package com.example.ebankback.entities;

import com.example.ebankback.entities.ennum.AccountStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE", length = 4)
public abstract class BankAccount {
    @Id //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private double balance;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "bankAccounts", fetch = FetchType.EAGER)
    private List<AccountOperation> accountOperations;
}
