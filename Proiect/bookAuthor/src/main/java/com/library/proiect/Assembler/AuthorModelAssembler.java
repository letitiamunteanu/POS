package com.library.proiect.Assembler;

import com.library.proiect.Controller.AuthorController;
import com.library.proiect.Models.Author;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AuthorModelAssembler implements RepresentationModelAssembler<Author, EntityModel<Author>> {

    @Override
    public EntityModel<Author> toModel(Author author) {

        return EntityModel.of(author, //
                linkTo(methodOn(AuthorController.class).getAuthById(author.getId(), null)).withSelfRel(),
                linkTo(methodOn(AuthorController.class).getAuthors(null,null, null,null,null)).withRel("authors").expand());
    }

    public EntityModel<Author> toModel(Author author, Optional<Integer> pages, Optional<Integer> items, Optional<String> nume, Optional<String> match, String token) {

        return EntityModel.of(author, //
                linkTo(methodOn(AuthorController.class).getAuthById(author.getId(), null)).withSelfRel(),
                linkTo(methodOn(AuthorController.class).getAuthors(pages, items,nume,match,token)).withRel("authors").expand());
    }
}
