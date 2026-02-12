package com.salvatore.validation.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="autori")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Autore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    private String nome;
    private String cognome;
    private String email;
    private String dataDiNascita;
    private String password;
    private String avatar;

    public Autore(String nome, String cognome, String email, String password, String dataDiNascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.dataDiNascita = dataDiNascita;
        this.avatar = "https://ui-avatars.com/api/?name=" + nome + "+" + cognome;
    }
}
