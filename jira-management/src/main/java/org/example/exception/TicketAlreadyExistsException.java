package org.example.exception;

public class TicketAlreadyExistsException extends Exception{
    public TicketAlreadyExistsException(String message) {
        super(message);
    }
}
