package com.library.proiect.Assembler;

import com.library.proiect.Controller.BookController;
import com.library.proiect.Models.Book;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookModelAssembler implements RepresentationModelAssembler<Book, EntityModel<Book>> {

    @Override
    public EntityModel<Book> toModel(Book book) {

        return EntityModel.of(book, //
                linkTo(methodOn(BookController.class).getBookById(book.getId(), null)).withSelfRel().expand(),
                linkTo(methodOn(BookController.class).getBooks(null, null,null,null)).withRel("books").expand());
    }

    public EntityModel<Book> toModel(Book book, Optional<Integer> pages, Optional<Integer> items,
                                     Optional<String> genre, Optional<Integer> year) {

        return EntityModel.of(book, //
                linkTo(methodOn(BookController.class).getBookById(book.getId(), null)).withSelfRel().expand(),
                linkTo(methodOn(BookController.class).getBooks(pages, items, genre, year)).withRel("books").expand());
    }

}
