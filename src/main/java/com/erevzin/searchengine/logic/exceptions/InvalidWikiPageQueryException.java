package com.erevzin.searchengine.logic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidWikiPageQueryException extends RuntimeException {
    public InvalidWikiPageQueryException(String message) {
        super(message);
    }
}
