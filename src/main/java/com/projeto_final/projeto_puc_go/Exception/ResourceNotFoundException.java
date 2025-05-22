// src/main/java/com/projeto_final/projeto_puc_go/exception/ResourceNotFoundException.java
package com.projeto_final.projeto_puc_go.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Esta anotação (@ResponseStatus) é mais para uso com a API REST,
// mas a classe Exception em si é útil para o console também.
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}