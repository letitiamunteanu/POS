package com.example.orders.Advise;


import com.example.orders.Exception.OrdersABCException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OrdersABCAdvise {

    @ResponseBody
    @ExceptionHandler(OrdersABCException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    String orderABCHandler(OrdersABCException ex){
        return ex.getMessage();
    }
}
