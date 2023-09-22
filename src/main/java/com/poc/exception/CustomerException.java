package com.poc.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomerException extends RuntimeException{

        private final HttpStatus httpStatus;

        public CustomerException(){
            super("Customer operation failed");
            this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        public CustomerException(String message){
            super(message);
            this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        public CustomerException(String message, HttpStatus status){
            super(message);
            this.httpStatus=status;
        }

}
