package com.example.orders.Advise;

import com.example.orders.Exception.BadTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class BadTokenAdvise {

    @ResponseBody
    @ExceptionHandler(BadTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String badTokenHandler(BadTokenException ex){

        return ex.getMessage();
    }

}
