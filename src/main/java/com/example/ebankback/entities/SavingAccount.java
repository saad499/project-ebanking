package com.example.ebankback.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
@DiscriminatorValue("BA")
public class SavingAccount extends BankAccount {
    public double interstRate;
}
