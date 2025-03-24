package com.ak.ecommerce_vender.error;

public class UserNotEnabledException extends RuntimeException{
    public UserNotEnabledException(String ex){
        super(ex);
    }
}
