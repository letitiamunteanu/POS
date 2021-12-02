package com.library.proiect.Controller;


import com.library.proiect.Assembler.AuthorModelAssembler;
import com.library.proiect.Models.Author;
import com.library.proiect.Services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorModelAssembler authorModelAssembler;

    @Autowired
    public AuthorController(AuthorService authorService, AuthorModelAssembler authorModelAssembler) {
        this.authorService = authorService;
        this.authorModelAssembler = authorModelAssembler;
    }

    @PostMapping //Create
    public ResponseEntity<?> addNewAuthor(@RequestBody Author auth){

        EntityModel<Author> entityModel = authorModelAssembler.toModel(authorService.addAuthor(auth));

        System.out.println(entityModel.getContent().getId());
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);

    }

    @GetMapping //Read
    public CollectionModel<?> getAuthors(@RequestParam("pages")Optional<Integer> pages, @RequestParam("items")Optional<Integer> items,
                                         @RequestParam("name")Optional<String> name, @RequestParam("match")Optional<String> match){

        List<EntityModel<Author>> auths = authorService.getAllAuthors(pages.orElse(0), items.orElse(3), name.orElse(""), match.orElse("")).stream() //
                .map(author -> authorModelAssembler.toModel(author,pages, items, name,match)) //
                .collect(Collectors.toList());

        return CollectionModel.of(auths, linkTo(methodOn(AuthorController.class).getAuthors(pages, items, name,match)).withSelfRel().expand());
    }

    @GetMapping("/{id}") //Read
    public EntityModel<?> getAuthById(@PathVariable Integer id){

        Author auth = authorService.getAuthorById(id);
        return authorModelAssembler.toModel(auth);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuthor(@RequestBody Author author, @PathVariable Integer id){

        Author auth = authorService.updateAuth(author,id);
        EntityModel<Author> entityModel = authorModelAssembler.toModel(author);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);

    }

    @DeleteMapping("/{id}") //Delete
    public ResponseEntity<?>  deleteAuthor(@PathVariable Integer id){
        authorService.deleteAuth(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value= "/allauthors", method = RequestMethod.OPTIONS)
    ResponseEntity<?> collectionOptions(){

        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS).build();
    }

    @RequestMapping(value="/author/{id}", method = RequestMethod.OPTIONS)
    ResponseEntity<?> singularOptions(){
        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.OPTIONS).build();
    }
}
