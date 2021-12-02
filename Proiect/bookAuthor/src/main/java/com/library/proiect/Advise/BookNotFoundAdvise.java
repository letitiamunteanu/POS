package com.library.proiect.Advise;

import com.library.proiect.Exception.BookExceptionNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BookNotFoundAdvise {

    @ResponseBody
    @ExceptionHandler(BookExceptionNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String bookNotFoundHandler(BookExceptionNotFound ex){
        return ex.getMessage();
    }
}
