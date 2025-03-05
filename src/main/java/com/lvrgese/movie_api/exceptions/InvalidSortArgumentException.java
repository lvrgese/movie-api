package com.lvrgese.movie_api.exceptions;

public class InvalidSortArgumentException extends RuntimeException {
    public InvalidSortArgumentException(String message) {
        super(message);
    }
}
