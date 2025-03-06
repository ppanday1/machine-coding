package org.example.exception;

public class EntityAlreadyFoundException extends Exception{
    public EntityAlreadyFoundException(String message) {
        super(message);
    }
}
