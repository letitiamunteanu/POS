package com.library.proiect.Models;

import javax.persistence.*;

@Table(name = "carte_autor", indexes = {
        @Index(name = "fk_isbn_idx", columnList = "isbn"),
        @Index(name = "fk_id_autor_idx", columnList = "id_autor")
})
@Entity
@IdClass(BookAuthorId.class)
public class BookAuthor {

    @Id
    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Id
    @Column(name = "id_autor", nullable = false)
    private Integer idAutor;

    @Column(name = "`index`", nullable = false)
    private Integer index;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getIsbn() {
        return isbn;
    }

    public BookAuthor() {
    }

    public BookAuthor(String isbn, Integer idAutor, Integer index) {
        this.isbn = isbn;
        this.idAutor = idAutor;
        this.index = index;
    }

    public Integer getIdAutor() {
        return idAutor;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setIdAutor(Integer idAutor) {
        this.idAutor = idAutor;
    }
}