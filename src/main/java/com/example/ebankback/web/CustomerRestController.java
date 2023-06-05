package com.example.ebankback.web;

import com.example.ebankback.dtos.CustomerDTO;
import com.example.ebankback.exceptions.CustomerNotFoundException;
import com.example.ebankback.exceptions.IllegalArgumentException;
import com.example.ebankback.services.BankAccountSerivce;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import com.example.ebankback.exceptions.ErrorResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public CustomerDTO getCustomer(@PathVariable(name = "id") String customerId) throws CustomerNotFoundException, IllegalArgumentException {
        try{
            Long customerIdLong = Long.parseLong(customerId);
            if(customerIdLong <=0){
                throw new IllegalArgumentException("Invalid customer Id: "+customerId);
            }
            CustomerDTO customerDTO = bankAccountSerivce.getCustomer(customerIdLong);
            if(customerDTO == null){
                throw new CustomerNotFoundException("Customer not found with id: "+customerIdLong);
            }
            return customerDTO;
        }catch (NumberFormatException e){
            throw new IllegalArgumentException("Invalid customer ID : " + customerId);
        }
    }



    @GetMapping("/customer/search")
    public List<CustomerDTO> searchCustomers(@RequestParam (name="keyword", defaultValue="") String keyword){
        return bankAccountSerivce.search(keyword);
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody @Valid CustomerDTO customerDTO){
        return bankAccountSerivce.saveCustomer(customerDTO);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException ex){
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        List<String> errorMessages = new ArrayList<>();
        for(FieldError error : fieldErrors){
            errorMessages.add(error.getDefaultMessage());
        }
        return new ErrorResponse("Validation error", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/customer/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO) throws CustomerNotFoundException, IllegalArgumentException{
        customerDTO.setId(customerId);
        return bankAccountSerivce.updateCustomer(customerDTO);
    }

    @DeleteMapping("/customer/{customerId}")
    public CustomerDTO deleteCustomer(@PathVariable(name = "customerId") String customerId) throws CustomerNotFoundException, IllegalArgumentException{
        try{
            Long customerIdLong = Long.parseLong(customerId);
            if(customerIdLong <=0){
                throw new IllegalArgumentException("Invalid customer Id: "+customerId);
            }
            CustomerDTO customerDTO = bankAccountSerivce.deleteCustomer(customerIdLong);
            if(customerDTO == null){
                throw new CustomerNotFoundException("Customer not found with id: "+customerIdLong);
            }
            return customerDTO;
        }catch(NumberFormatException e) {
            throw new IllegalArgumentException("Invalid customer ID : " + customerId);
        }
    }

    /*
    *
    * @GetMapping("/customer/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") String customerId) throws CustomerNotFoundException, IllegalArgumentException {
        try{
            Long customerIdLong = Long.parseLong(customerId);
            if(customerIdLong <=0){
                throw new IllegalArgumentException("Invalid customer Id: "+customerId);
            }
            CustomerDTO customerDTO = bankAccountSerivce.getCustomer(customerIdLong);
            if(customerDTO == null){
                throw new CustomerNotFoundException("Customer not found with id: "+customerIdLong);
            }
            return customerDTO;
        }catch (NumberFormatException e){
            throw new IllegalArgumentException("Invalid customer ID : " + customerId);
        }
    }
    * */

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCustomerNotFoundException(CustomerNotFoundException ex) {
        return new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException ex){
        return new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
