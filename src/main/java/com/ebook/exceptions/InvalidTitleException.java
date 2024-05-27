package com.ebook.exceptions;

public class InvalidTitleException extends RuntimeException {
    public InvalidTitleException() {
        super("Title cannot be null or empty");
    }
}
