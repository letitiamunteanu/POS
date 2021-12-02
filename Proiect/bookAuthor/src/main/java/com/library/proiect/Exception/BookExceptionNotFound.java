package com.library.proiect.Exception;

public class BookExceptionNotFound extends RuntimeException {

   public BookExceptionNotFound(String id){
       super("Could not find the book with isbn " + id);
   }
}
