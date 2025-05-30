package com.lvrgese.movie_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmptyFileException.class)
    public ProblemDetail emptyFileExceptionHandler(EmptyFileException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,ex.getMessage());
    }

    @ExceptionHandler(FileAlreadyExistsException.class)
    public ProblemDetail fileAlreadyExistsExceptionHandler(FileAlreadyExistsException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,ex.getMessage());
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ProblemDetail movieNotFoundExceptionHandler(MovieNotFoundException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,ex.getMessage());
    }

    @ExceptionHandler(InvalidSortArgumentException.class)
    public ProblemDetail invalidSortArgumentExceptionHandler(InvalidSortArgumentException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,ex.getMessage());
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ProblemDetail userAlreadyExistsExceptionHandler(UserAlreadyExistsException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,ex.getMessage());
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ProblemDetail invalidRefreshTokenExceptionHandler(InvalidRefreshTokenException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,ex.getMessage());
    }

}
