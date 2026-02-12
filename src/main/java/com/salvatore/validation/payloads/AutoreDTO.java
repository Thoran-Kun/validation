package com.salvatore.validation.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record AutoreDTO(
        @NotBlank(message = "Il nome proprio è un campo obbligatorio")
        @Size(min = 2, max = 30, message = "Il nome proprio deve rientrare tra i 2 e i 30 caratteri")
        String nome,
        @NotBlank(message = "Il cognome è un campo obbligatorio")
        @Size(min = 2, max = 30, message = "Il cognome deve essere tra i 2 e i 30 caratteri")
        String cognome,
        @NotBlank(message = "La password è obligatoria")
        @Email(message = "la password deve avere almeno 4 caratteri")
        String email,
        @NotBlank(message = "La password è obbligatoria")
        @Size(min = 4, message = "La password deve avere almeno 4 caratteri")
        String password,

        @NotNull(message = "La data di nascita è obbligatoria")
        @Past(message = "La data di nascita deve essere nel passato")
        @JsonFormat(pattern = "yyyy-MM-dd")
        String dataDiNascita
) {
}
