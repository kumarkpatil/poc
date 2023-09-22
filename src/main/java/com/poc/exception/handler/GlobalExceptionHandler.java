package com.poc.exception.handler;

import com.poc.exception.CustomerException;
import com.poc.exception.model.CustomError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.Random;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private final Random random = new Random();
    public Mono<ResponseEntity<CustomError>> handleCustomerException(CustomerException ex){
        log.error(ex.getMessage());

        return Mono.just(buildResponse(ex));
    }

    private ResponseEntity<CustomError> buildResponse(CustomerException ex) {
     return new ResponseEntity<>(CustomError.builder().errorId(random.nextLong())
                .errorMessage(ex.getMessage())
                .httpStatus(ex.getHttpStatus()).build(),ex.getHttpStatus()
        );
    }
}
