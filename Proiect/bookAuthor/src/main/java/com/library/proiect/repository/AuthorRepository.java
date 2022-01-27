package com.library.proiect.repository;

import com.library.proiect.Models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    List<Author> findByNume(String name);
    List<Author> findByNumeContaining(String name);
}
