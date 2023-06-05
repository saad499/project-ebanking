package com.example.ebankback.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;

    @NotEmpty(message="Name is required")
    @Size(max = 20, message="Name cannot exceed 20 characters")
    private String name;
    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 20, message = "Email cannot exceed 20 characters")
    private String email;
}
