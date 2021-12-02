package com.example.orders.Advise;

import com.example.orders.Exception.Error404Exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class Error404Advise {

    @ResponseBody
    @ExceptionHandler(Error404Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String error404Handle(Error404Exception ex){

        return ex.getMessage();
    }
}
