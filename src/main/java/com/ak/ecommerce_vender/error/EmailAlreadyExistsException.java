package com.ak.ecommerce_vender.error;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(String ex){
        super(ex);
    }
}
