package com.library.proiect.Controller;

import com.library.proiect.Models.Author;
import com.library.proiect.Models.BookAuthor;
import com.library.proiect.Services.AuthorService;
import com.library.proiect.Services.BookAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books-authors")
public class BookAuthorController {


    private final BookAuthorService bookAuthorService;
    private final AuthorService authorService;

    @Autowired
    public BookAuthorController(BookAuthorService bookAuthorService, AuthorService authorService) {
        this.bookAuthorService = bookAuthorService;
        this.authorService = authorService;
    }


    @GetMapping("/{isbn}")
    public List<Author> getAuthorByISBN(@PathVariable String isbn){

        List<BookAuthor> list = bookAuthorService.getAuthorsByISBN(isbn);
        List<Author> list1 = new ArrayList<>();

        for (BookAuthor ba : list) {

            list1.add(authorService.getAuthorById(ba.getIdAutor()));
        }

        return list1;
    }
}
