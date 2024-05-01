package com.deg.country;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class RetryException extends RuntimeException{

    public RetryException(String message) {
        super(message);
    }

}
