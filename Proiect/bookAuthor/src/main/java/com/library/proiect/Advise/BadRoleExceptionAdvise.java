package com.library.proiect.Advise;

import com.library.proiect.Exception.BadRoleException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BadRoleExceptionAdvise {

    @ResponseBody
    @ExceptionHandler(BadRoleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String badRoleExceptionHandler(BadRoleException ex){
        return ex.getMessage();
    }
}
