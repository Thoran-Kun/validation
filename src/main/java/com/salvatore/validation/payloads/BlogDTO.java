package com.salvatore.validation.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record BlogDTO(
        @NotEmpty(message = "La categoria è obbligatoria")
        String categoria,

        @NotEmpty(message = "Il titolo è obbligatorio")
        @Size(min = 2, max = 100, message = "Il titolo deve essere tra 2 e 100 caratteri")
        String titolo,

        @NotEmpty(message = "La cover è obbligatoria")
        String cover,

        @NotEmpty(message = "Il contenuto è obbligatorio")
        String contenuto,

        @NotNull(message = "Il tempo di lettura è obbligatorio")
        @Positive(message = "Il tempo di lettura deve essere maggiore di zero")
        int tempoDiLettura,

        @NotNull(message = "L'ID dell'autore è obbligatorio")
        long autoreId
) {
}