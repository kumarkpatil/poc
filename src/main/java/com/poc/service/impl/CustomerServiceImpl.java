package com.poc.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.exception.CustomerException;
import com.poc.model.Customer;
import com.poc.service.CustomerService;
import com.poc.util.CommonUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.poc.Constant.CustomerConstant.BASE_PATH;
import static com.poc.Constant.CustomerConstant.FILE_NAME;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    List<Customer> customerList = new ArrayList<>();

    @Autowired
    ObjectMapper objectMapper;

    @PostConstruct
    public void init(){
        try {
            File file = new File(BASE_PATH+FILE_NAME);
            log.info("Is File exist : {}",file.exists());
            if(!file.exists()) {
                /*If file not exists, create new one*/
                new FileWriter(BASE_PATH + FILE_NAME);
            }
            else{

                FileInputStream inputStream = new FileInputStream(file);
                TypeReference<List<Customer>> typeReference = new TypeReference<List<Customer>>(){};
                if(inputStream.readAllBytes().length>2) {
                    customerList = objectMapper.readValue(inputStream, typeReference);
                }
                log.info("Customer List : {}",customerList);
            }
        } catch (IOException e) {
            e.printStackTrace(); //TODO: need to handle exception
        }
        log.info("Init method completed");
    }


    @Override
    public Mono<Customer> getCustomer(Integer id) {
        Optional<Customer> customerObject = customerList.stream().filter(customer -> customer.getId().equals(id)).findFirst();
        if(customerObject.isPresent()){
            return Mono.just(customerObject.get());
        } else {
            throw new CustomerException("Customer not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Flux<Customer> getCustomers() {
        return Flux.fromIterable(customerList);
    }

    @Override
    public Flux<Customer> findCustomers(String key) {
        return Flux.fromIterable(customerList.stream().filter(customer -> customer.getName().equals(key) || customer.getLastName().equals(key)
                || customer.getEmail().equals(key)).toList());
    }

    @Override
    public Mono<Customer> addCustomer(Customer customer) {
         customerList.add(customer);
         customer.setPassword(CommonUtils.masked(customer.getPassword()));
         return Mono.just(customer);
    }


    @PreDestroy
    public void save(){
        try {
            File file = new File(BASE_PATH+"customer.json");
            objectMapper.writeValue(file, customerList);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        log.info("Data written successfully");
    }

}
