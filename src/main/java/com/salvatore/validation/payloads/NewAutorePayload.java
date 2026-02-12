package com.salvatore.validation.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NewAutorePayload {
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String dataDiNascita;
}
