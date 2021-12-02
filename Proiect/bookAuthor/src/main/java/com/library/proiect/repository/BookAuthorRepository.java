package com.library.proiect.repository;

import com.library.proiect.Models.BookAuthor;
import com.library.proiect.Models.BookAuthorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookAuthorRepository extends JpaRepository<BookAuthor, BookAuthorId> {

    List<BookAuthor> findByIsbn(String isbn);
}
