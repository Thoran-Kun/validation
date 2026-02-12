package com.salvatore.validation.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Random;

@Entity
@Table(name="blogs")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    private String categoria;
    private String titolo;
    private String cover;
    private String contenuto;
    private int tempoDiLettura;

    @ManyToOne
    @JoinColumn(name = "autore_id")
    private Autore autore;

    public Blog(String categoria, String titolo, String contenuto, int tempoDiLettura) {
        Random rndm = new Random();
        this.id = rndm.nextInt(1, 1000);
        this.categoria = categoria;
        this.titolo = titolo;
        this.contenuto = contenuto;
        this.tempoDiLettura = tempoDiLettura;
        this.cover = "https://picsum.photos/200/300?random=" + this.id;
    }
}
