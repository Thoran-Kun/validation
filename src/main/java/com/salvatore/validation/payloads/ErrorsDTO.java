package com.salvatore.validation.payloads;

import java.time.LocalDateTime;

public record ErrorsDTO(String message, LocalDateTime timpestamp) {
}
