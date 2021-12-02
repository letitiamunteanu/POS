package com.library.proiect.Services;

import com.library.proiect.Exception.BookExceptionNotFound;
import com.library.proiect.Exception.BookQuantityException;
import com.library.proiect.Models.Book;
import com.library.proiect.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }


    //POST - Create
    public Book addBook(Book bookToAdd){

        if(bookRepository.existsById(bookToAdd.getId())){
            throw new IllegalStateException("The book you wanna add already exists.");
        }
        else if(bookToAdd.getTitlu() != null &&
           bookToAdd.getGenLiterar()!= null &&
           bookToAdd.getAnPublicare() != null &&
           bookToAdd.getId() != null &&
           bookToAdd.getEditura() != null){

            bookRepository.save(bookToAdd);
        }
        return bookToAdd;
    }


    //GET -  Read
    public List<Book> getAllBooks(Integer pages, Integer items, String genre, Integer year){

        List<Book> listOfAllBooks = bookRepository.findAll();
        if(!Objects.equals(genre, ""))
        {
            listOfAllBooks = listOfAllBooks.stream().filter(book -> book.getGenLiterar().equals(genre)).collect(Collectors.toList());
        }
        if(year != 0){
            listOfAllBooks = listOfAllBooks.stream().filter((book -> book.getAnPublicare().equals(year))).collect(Collectors.toList());
        }
        if(pages != 0 && items !=0 ){

            listOfAllBooks = listOfAllBooks.stream().skip((long)(pages - 1) * items).limit(items).collect(Collectors.toList());
        }

        return listOfAllBooks;
    }

    public Book getBookById(String id, String verbose){

        Book myBook =  bookRepository.findById(id).orElseThrow(() -> new BookExceptionNotFound(id));
        Book newBook = new Book();

        if(verbose.equals("false")){
            newBook.setId(myBook.getId());
            newBook.setGenLiterar(myBook.getGenLiterar());
            newBook.setTitlu(myBook.getTitlu());

            return newBook;
        }

        return myBook;
    }

    public Book getBookByBlockedQuantity(String id, Integer quantity){

        Book myBook = bookRepository.findById(id).orElseThrow(() -> new BookExceptionNotFound(id));
        if(myBook.getCantitate() >= quantity){
            myBook.setCantitate(myBook.getCantitate() - quantity);
            myBook.setCantitateVanduta(myBook.getCantitateVanduta() + quantity);
            bookRepository.save(myBook);
        }
        else{
            throw new BookQuantityException("The available quantity is less than the selected one");
        }
        return myBook;
    }

    public String getBookByReleasedQuantity(String id, Integer quantity){

        Book myBook = bookRepository.findById(id).orElseThrow(() -> new BookExceptionNotFound(id));

        if(myBook.getCantitateVanduta() <= quantity){
            throw new BookQuantityException("You wanna delete a quantity that is greater than the blocked one!");
        }
        else {
            myBook.setCantitateVanduta(myBook.getCantitateVanduta() - quantity);
            myBook.setCantitate(myBook.getCantitate() + quantity);
            bookRepository.save(myBook);
        }
        return "ok";
    }

    @Transactional
    public String finalizeOrder(Map<String,Integer> booksToFinalize){

        booksToFinalize.keySet().forEach(key->{
            Book b = bookRepository.findById(key).orElseThrow(()-> new BookExceptionNotFound(key));

            if(b.getCantitateVanduta() >= booksToFinalize.get(key)){
                b.setCantitateVanduta(b.getCantitateVanduta() - booksToFinalize.get(key));
                bookRepository.save(b);
            }
            else{
                throw new BookQuantityException("This quantity is not available in stock for book with id " + key);
            }
        });

        return "ok";
    }

    //PUT - UPDATE
    public Book UpdateBook(String id, Book bookToUpdate){

        return bookRepository.findById(id).map(b -> {
            b.setTitlu(bookToUpdate.getTitlu());
            b.setEditura(bookToUpdate.getEditura());
            b.setGenLiterar(bookToUpdate.getGenLiterar());
            b.setAnPublicare(bookToUpdate.getAnPublicare());
            return bookRepository.save(bookToUpdate);
        }).orElseGet(() -> {
            bookToUpdate.setId(id);
            return bookRepository.save(bookToUpdate);
        });
    }

    //DELETE - D
    public void deleteBook(String id){
        if(bookRepository.existsById(id)){
            bookRepository.deleteById(id);
        }
        else{
            throw new IllegalStateException("Book with isbn: " + id + " doesn't exist");
        }
    }
}
