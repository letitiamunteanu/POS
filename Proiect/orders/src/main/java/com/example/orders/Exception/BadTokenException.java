package com.example.orders.Exception;

public class BadTokenException extends RuntimeException{

    public BadTokenException(String message){

        super(message);
    }
}
