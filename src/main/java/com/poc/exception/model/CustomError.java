package com.poc.exception.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class CustomError {

    private Long errorId;
    private String errorMessage;
    private HttpStatus httpStatus;
}
