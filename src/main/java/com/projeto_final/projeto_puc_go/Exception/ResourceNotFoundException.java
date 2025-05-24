// src/main/java/com/projeto_final/projeto_puc_go/exception/ResourceNotFoundException.java
package com.projeto_final.projeto_puc_go.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}