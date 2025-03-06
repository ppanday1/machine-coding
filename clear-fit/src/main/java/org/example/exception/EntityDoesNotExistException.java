package org.example.exception;

public class EntityDoesNotExistException extends Exception{
    public EntityDoesNotExistException(String message) {
        super(message);
    }
}
