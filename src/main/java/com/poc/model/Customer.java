package com.poc.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {

    private Integer id;
    private String name;
    private String lastName;
    private String email;
    private Integer age;
    private String password;


}
