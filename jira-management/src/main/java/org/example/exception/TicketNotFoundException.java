package org.example.exception;

public class TicketNotFoundException extends Exception{
    public TicketNotFoundException(String message) {
        super(message);
    }
}
