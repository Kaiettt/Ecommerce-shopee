package com.ak.ecommerce_vender.error;

public class EntityNotExistException extends RuntimeException{
    public EntityNotExistException(String ex){
        super(ex);
    }
}
