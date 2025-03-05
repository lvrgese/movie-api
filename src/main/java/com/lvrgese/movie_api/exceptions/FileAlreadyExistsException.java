package com.lvrgese.movie_api.exceptions;

public class FileAlreadyExistsException extends RuntimeException{
    public FileAlreadyExistsException(String message){
        super(message);
    }
}
