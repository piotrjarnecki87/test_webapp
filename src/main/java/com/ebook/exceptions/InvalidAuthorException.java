package com.ebook.exceptions;

public class InvalidAuthorException extends RuntimeException {
    public InvalidAuthorException() {
        super("Author cannot be null or empty");
    }
}
