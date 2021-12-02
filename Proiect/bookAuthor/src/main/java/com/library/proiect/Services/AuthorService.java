package com.library.proiect.Services;

import com.library.proiect.Exception.AuthorExceptionNotFound;
import com.library.proiect.Models.Author;
import com.library.proiect.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    //POST- CREATE
    public Author addAuthor(Author author){

        if(author.getNume() != null &&  author.getPrenume() != null){
            authorRepository.save(author);
        }
        return author;
    }

    //GET - READ
    public List<Author> getAllAuthors(Integer pages, Integer items, String name, String match){

//        List<Author> listOfAllAuthors = authorRepository.findAll();
//
//        if(name != "" && match.matches("exact")){
//            listOfAllAuthors = listOfAllAuthors.stream().filter(author -> author.getNume().matches(name)).collect(Collectors.toList());
//        }
//        else if(name != ""){
//            listOfAllAuthors = listOfAllAuthors.stream().filter(author -> author.getNume().contains(name)).collect(Collectors.toList());
//        }
//        return listOfAllAuthors;

        List<Author> authorToFind = authorRepository.findAll();
        if (name != "" && match.matches("exact")){
            authorToFind = authorRepository.findByNume(name);
        }
        else if(name != ""){
            authorToFind = authorRepository.findByNumeContaining(name);
        }
        if(pages != 0 && items != 0){
            authorToFind = authorToFind.stream().skip((long)(pages - 1) * items).limit(items).collect(Collectors.toList());
        }
        return authorToFind;
    }

    public Author getAuthorById(Integer id){
        return authorRepository.findById(id).orElseThrow(() -> new AuthorExceptionNotFound(id));
    }

    //PUT - UPDATE
    public Author updateAuth(Author author, Integer id){

        return authorRepository.findById(id).map(b -> {

            author.setNume(author.getNume());
            author.setPrenume(author.getPrenume());
            return authorRepository.save(author);

        }).orElseGet(() -> {
            author.setId(id);
            return authorRepository.save(author);
        });
    }

    //DELETE - DELETE
    public void deleteAuth(Integer id){

        if(authorRepository.existsById(id)){
            authorRepository.deleteById(id);
        }
        else{
            throw new IllegalStateException("Author with id: " + id + " doesn't exist");
        }
    }

}
