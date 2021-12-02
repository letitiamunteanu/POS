package com.library.proiect.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "carte", indexes = {
        @Index(name = "idx_an", columnList = "an_publicare"),
        @Index(name = "index4", columnList = "gen_literar"),
        @Index(name = "titlu_UNIQUE", columnList = "titlu", unique = true)
})

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @Column(name = "isbn", nullable = false, length = 20)
    private String id;

    @Column(name = "titlu", nullable = false, length = 45)
    private String titlu;

    @Column(name = "an_publicare", nullable = false)
    private Integer anPublicare;

    @Column(name = "gen_literar", nullable = false, length = 45)
    private String genLiterar;

    @Column(name = "editura", nullable = false, length = 45)
    private String editura;

    @Column(name = "pret", nullable = false)
    private Integer pret;

    @Column(name = "cantitate", nullable = false)
    private Integer cantitate;

    @Column(name = "cantitate_vanduta", nullable = false)
    private Integer cantitateVanduta;

}