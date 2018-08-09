package com.devplant.wallapp.controller;

import com.devplant.wallapp.exceptions.AccountAlreadyExistsException;
import com.devplant.wallapp.exceptions.ErrorMessage;
import com.devplant.wallapp.exceptions.ForbiddenException;
import com.devplant.wallapp.exceptions.ObjectNotFoundException;
import com.devplant.wallapp.exceptions.PasswordDoesNotMatchException;
import com.devplant.wallapp.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = { ObjectNotFoundException.class })
    public ErrorMessage handleNotFoundException(ObjectNotFoundException e) {
        return new ErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = { AccountAlreadyExistsException.class })
    public ErrorMessage handleAccountAlreadyExistsException(AccountAlreadyExistsException e) {
        return new ErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(value = { PasswordDoesNotMatchException.class })
    public ErrorMessage handlePasswordDoesNotMatchException(PasswordDoesNotMatchException e) {
        return new ErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = { ForbiddenException.class })
    public ErrorMessage handleForbiddenException(ForbiddenException e) {
        return new ErrorMessage(e.getMessage());
    }


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = { UnauthorizedException.class })
    public ErrorMessage handleUNAUTHORIZED(UnauthorizedException e) {
        return new ErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ExceptionHandler(value = { ConstraintViolationException.class })
    public ErrorMessage handleConstraintViolationException(ConstraintViolationException e) {
        return new ErrorMessage(e.getMessage());
    }

}
