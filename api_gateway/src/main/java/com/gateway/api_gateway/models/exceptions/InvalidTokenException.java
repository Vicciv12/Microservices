package com.gateway.api_gateway.models.exceptions;

public class InvalidTokenException extends Exception{
    
    public InvalidTokenException(String msg){
        super(msg);
    }

}
