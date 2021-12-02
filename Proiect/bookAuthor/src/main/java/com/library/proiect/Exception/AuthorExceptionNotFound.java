package com.library.proiect.Exception;

public class AuthorExceptionNotFound extends RuntimeException {
    public AuthorExceptionNotFound(Integer id){
        super("Could not find the author with id " + id);
    }
}
