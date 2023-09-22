package com.poc.service;

import com.poc.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CustomerService {

    public Mono<Customer> getCustomer(Integer id);

    public Flux<Customer> getCustomers();
    public Flux<Customer> findCustomers(String key);

    public Mono<Customer> addCustomer(Customer customer);


}
