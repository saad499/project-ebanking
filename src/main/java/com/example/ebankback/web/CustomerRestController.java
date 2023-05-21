package com.example.ebankback.web;

import com.example.ebankback.dtos.CustomerDTO;
import com.example.ebankback.services.BankAccountSerivce;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@Slf4j
public class CustomerRestController {
    private BankAccountSerivce bankAccountSerivce;

    @GetMapping("/customer")
    public List<CustomerDTO> custumers(){
        return bankAccountSerivce.listCustomer();
    }

    @GetMapping("/customer/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId){
        return bankAccountSerivce.getCustomer(customerId);
    }

    @GetMapping("/customer/search")
    public List<CustomerDTO> searchCustomers(@RequestParam (name="keyword", defaultValue="") String keyword){
        return bankAccountSerivce.search(keyword);
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return bankAccountSerivce.saveCustomer(customerDTO);
    }

    @PutMapping("/customer/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO){
        customerDTO.setId(customerId);
        return bankAccountSerivce.updateCustomer(customerDTO);
    }

    @DeleteMapping("/customer/{customerId}")
    public CustomerDTO deleteCustomer(@PathVariable Long customerId){
        return bankAccountSerivce.deleteCustomer(customerId);
    }
}
