package com.library.proiect.Services;

import com.library.proiect.Exception.AuthorExceptionNotFound;
import com.library.proiect.Exception.BadRoleException;
import com.library.proiect.Exception.BadTokenException;
import com.library.proiect.Models.Author;
import com.library.proiect.SOAP.SoapRequest;
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
    public Author addAuthor(Author author, String token){

        String tkn = SoapRequest.SoapTokenRequest(token);

        //we can add a book only if we are admin
        if(!tkn.equals("Expired") && !tkn.equals("Invalid") && tkn.split(" ")[1].equals("admin")) {
            if (author.getNume() != null && author.getPrenume() != null) {
                authorRepository.save(author);
            }
            return author;
        }
        else {
            throw new BadRoleException("add authors");
        }

    }

    //GET - READ
    public List<Author> getAllAuthors(Integer pages, Integer items, String name, String match, String token){

        String tkn = SoapRequest.SoapTokenRequest(token);

        if(!tkn.equals("Expired") && !tkn.equals("Invalid")) {

            List<Author> authorToFind = authorRepository.findAll();
            if (name != "" && match.matches("exact")) {
                authorToFind = authorRepository.findByNume(name);
            } else if (name != "") {
                authorToFind = authorRepository.findByNumeContaining(name);
            }
            if (pages != 0 && items != 0) {
                authorToFind = authorToFind.stream().skip((long) (pages - 1) * items).limit(items).collect(Collectors.toList());
            }
            return authorToFind;
        }
        else {
            throw new BadTokenException(tkn);
        }
    }

    public Author getAuthorById(Integer id, String token) {

        String tkn = SoapRequest.SoapTokenRequest(token);

        if (!tkn.equals("Expired") && !tkn.equals("Invalid")) {

            return authorRepository.findById(id).orElseThrow(() -> new AuthorExceptionNotFound(id));
        }
        else {
            throw new BadTokenException(tkn);
        }
    }


    //PUT - UPDATE
    public Author updateAuth(Author author, Integer id, String token){

        String tkn = SoapRequest.SoapTokenRequest(token);

        //we can add a book only if we are admin
        if(!tkn.equals("Expired") && !tkn.equals("Invalid") && tkn.split(" ")[1].equals("admin")) {
            return authorRepository.findById(id).map(b -> {

                author.setNume(author.getNume());
                author.setPrenume(author.getPrenume());
                return authorRepository.save(author);

            }).orElseGet(() -> {
                author.setId(id);
                return authorRepository.save(author);
            });
        }
        else {
            throw new BadRoleException("update authors");
        }
    }

    //DELETE - DELETE
    public void deleteAuth(Integer id, String token){

        String tkn = SoapRequest.SoapTokenRequest(token);

        //we can add a book only if we are admin
        if(!tkn.equals("Expired") && !tkn.equals("Invalid") && tkn.split(" ")[1].equals("admin")) {
            if (authorRepository.existsById(id)) {
                authorRepository.deleteById(id);
            } else {
                throw new IllegalStateException("Author with id: " + id + " doesn't exist");
            }
        }
        else {
            throw new BadRoleException("delete authors");
        }
    }

}
