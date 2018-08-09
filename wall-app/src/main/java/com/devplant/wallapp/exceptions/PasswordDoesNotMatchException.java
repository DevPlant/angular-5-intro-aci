package com.devplant.wallapp.exceptions;


public class PasswordDoesNotMatchException extends RuntimeException {

    public PasswordDoesNotMatchException(String s) {
        super(s);
    }

}
