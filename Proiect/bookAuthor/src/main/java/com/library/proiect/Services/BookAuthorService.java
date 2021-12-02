package com.library.proiect.Services;

import com.library.proiect.Models.Author;
import com.library.proiect.Models.BookAuthor;
import com.library.proiect.repository.BookAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookAuthorService {

    private final BookAuthorRepository bookAuthorRepository;

    @Autowired
    public BookAuthorService(BookAuthorRepository bookAuthorRepository) {
        this.bookAuthorRepository = bookAuthorRepository;
    }

    public List<BookAuthor> getAuthorsByISBN(String isbn){

        return bookAuthorRepository.findByIsbn(isbn);
    }
}
