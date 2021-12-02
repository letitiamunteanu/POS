package com.library.proiect.Models;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Objects;

@IdClass(BookAuthorId.class)
public class BookAuthorId implements Serializable {
    private static final long serialVersionUID = -6069026168191255428L;
    @Column(name = "isbn", nullable = false, length = 45)
    private String isbn;
    @Column(name = "id_autor", nullable = false)
    private Integer idAutor;

    public Integer getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Integer idAutor) {
        this.idAutor = idAutor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAutor, isbn);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BookAuthorId entity = (BookAuthorId) o;
        return Objects.equals(this.idAutor, entity.idAutor) &&
                Objects.equals(this.isbn, entity.isbn);
    }
}