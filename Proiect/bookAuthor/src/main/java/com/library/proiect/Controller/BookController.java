package com.library.proiect.Controller;

import com.library.proiect.Assembler.BookModelAssembler;
import com.library.proiect.Models.Book;
import com.library.proiect.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping(value = "api/bookcollection/books", produces = {MediaType.APPLICATION_JSON_VALUE})
public class BookController {


    private final BookService bookService;
    private final BookModelAssembler bookModelAssembler;

    @Autowired
    public BookController(BookService bookService, BookModelAssembler bookModelAssembler){
        this.bookService = bookService;
        this.bookModelAssembler = bookModelAssembler;
    }

    @PostMapping
    public ResponseEntity<?> addNewBook(@RequestBody Book book, @RequestHeader("Authorization") String token){

        EntityModel<Book> entityModel = bookModelAssembler.toModel(bookService.addBook(book, token));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @GetMapping
    public CollectionModel<EntityModel<Book>> getBooks(@RequestParam("pages")Optional<Integer> pages, @RequestParam("items")Optional<Integer> items,
                                                       @RequestParam("genre")Optional<String> genre, @RequestParam("year")Optional<Integer> year, @RequestHeader("Authorization") String token){

        List<EntityModel<Book>> books = bookService.getAllBooks(pages.orElse(0), items.orElse(3), genre.orElse(""), year.orElse(0),token).stream() //
                .map(book -> bookModelAssembler.toModel(book,pages,items,genre,year,token)) //
                .collect(Collectors.toList());

        return CollectionModel.of(books, linkTo(methodOn(BookController.class).getBooks(pages, items,genre,year,token)).withSelfRel().expand());
    }


    @GetMapping("/{id}")
    public EntityModel<Book> getBookById(@PathVariable String id, @RequestParam("verbose")Optional<String> verbose, @RequestHeader("Authorization") String token){
        Book b = bookService.getBookById(id,verbose.orElse(""), token);
        return bookModelAssembler.toModel(b);
    }

    @PostMapping("/blockQuantity/{id}")
    public EntityModel<Book> getBookByQuantity(@PathVariable String id, @RequestParam Integer quantity, @RequestHeader("Authorization") String token){
        Book b = bookService.getBookByBlockedQuantity(id,quantity,token);
        return bookModelAssembler.toModel(b);
    }

    @PostMapping("/releaseQuantity/{id}")
    public ResponseEntity<?> getBookByReleasedQuantity(@PathVariable String id, @RequestParam Integer quantity, @RequestHeader("Authorization") String token){
        String result = bookService.getBookByReleasedQuantity(id,quantity,token);

        if(result.equals("ok")){
            return ResponseEntity.ok().body(result);
        }
        return null;
    }

    @PostMapping("/finalize")
    public ResponseEntity<?> FinalizeOrder(@RequestBody Map<String,Integer> booksToFinalize, @RequestHeader("Authorization") String token){
        return new ResponseEntity<>(bookService.finalizeOrder(booksToFinalize, token), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> UpdateBook(@PathVariable String id,@RequestBody Book book, @RequestHeader("Authorization") String token){

        Book b =  bookService.UpdateBook(id,book,token);
        EntityModel<Book> entityModel = bookModelAssembler.toModel(b);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteBook(@PathVariable String id, @RequestHeader("Authorization") String token){

        bookService.deleteBook(id,token);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value= "/allbbooks", method = RequestMethod.OPTIONS)
    ResponseEntity<?> collectionOptions(){

        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS).build();
    }

    @RequestMapping(value="/books/{id}", method = RequestMethod.OPTIONS)
    ResponseEntity<?> singularOptions(){
        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.OPTIONS).build();
    }
}
