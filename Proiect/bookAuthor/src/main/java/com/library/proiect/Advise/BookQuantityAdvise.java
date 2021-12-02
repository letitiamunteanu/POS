package com.library.proiect.Advise;

import com.library.proiect.Exception.BookExceptionNotFound;
import com.library.proiect.Exception.BookQuantityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BookQuantityAdvise {

    @ResponseBody
    @ExceptionHandler(BookQuantityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String bookQuantityHandler(BookQuantityException ex){
        return ex.getMessage();
    }
}
