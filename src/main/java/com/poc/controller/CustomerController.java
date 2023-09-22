package com.poc.controller;

import com.poc.model.Customer;
import com.poc.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@AllArgsConstructor
public class CustomerController {

    CustomerService customerService;

    @GetMapping("/customer/{id}")
    public Mono<Customer> getCustomer(@PathVariable Integer id){
        return customerService.getCustomer(id);
    }

    @GetMapping("/customers")
    public Flux<Customer> getCustomers(){
        return customerService.getCustomers();
    }

    @GetMapping("/customer/find")
    public Flux<Customer> findCustomers(@RequestParam String key){
        return customerService.findCustomers(key);
    }

    @PostMapping("/customer")
    public Mono<Customer> addCustomer(@RequestBody Customer customer){
        return customerService.addCustomer(customer);
    }


}
