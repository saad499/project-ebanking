package com.example.ebankback.dtos;

import com.example.ebankback.entities.ennum.OperationType;
import lombok.Data;

import java.util.Date;
@Data
public class AccountOperationDTO {
    private Long id;
    private Date operation;
    private double amount;
    private OperationType type;
    private String description;
}
