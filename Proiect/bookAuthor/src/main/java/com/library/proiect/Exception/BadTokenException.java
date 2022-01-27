package com.library.proiect.Exception;

public class BadTokenException extends RuntimeException{

    public BadTokenException(String message ){
        super("Tokenul dvs este " + message + " !");
    }
}
