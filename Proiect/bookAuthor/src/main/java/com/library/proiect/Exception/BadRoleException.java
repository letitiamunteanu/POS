package com.library.proiect.Exception;

public class BadRoleException extends RuntimeException{

    public BadRoleException(String message){
        super("You are not an admin, you cant " +  message);
    }
}
