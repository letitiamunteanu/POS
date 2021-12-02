package com.library.proiect.Models;

import javax.persistence.*;

@Table(name = "autor", indexes = {
        @Index(name = "idx_autor_nume", columnList = "nume"),
        @Index(name = "index_prenume", columnList = "prenume")
})
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "prenume", nullable = false, length = 45)
    private String prenume;

    @Column(name = "nume", nullable = false, length = 45)
    private String nume;

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}