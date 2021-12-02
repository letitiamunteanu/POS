package com.library.proiect.Advise;


import com.library.proiect.Exception.AuthorExceptionNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AuthorNotFoundAdvise {

    @ResponseBody
    @ExceptionHandler(AuthorExceptionNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String authorNotFoundHandler(AuthorExceptionNotFound ex){
        return ex.getMessage();
    }
}
