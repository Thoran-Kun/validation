package com.salvatore.validation.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class NewBlogPayload {
    long autoreId;
    private String categoria;
    private String titolo;
    private String contenuto;
    private int tempoDiLettura;
}
