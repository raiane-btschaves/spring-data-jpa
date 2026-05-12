package io.github.raianebtschaves.libraryapi.exceptions;

public class OperationNotPermittedException extends RuntimeException {

    public OperationNotPermittedException(String message) {
        super(message);
    }
}
