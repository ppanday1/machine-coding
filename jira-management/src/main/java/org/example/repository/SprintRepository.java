package org.example.repository;

import org.example.model.Ticket;

import java.util.concurrent.ConcurrentHashMap;

public class SprintRepository {
    private final ConcurrentHashMap<String, Ticket> tickets; //managing only current sprint- leaving other checks

    public SprintRepository() {
        this.tickets = new ConcurrentHashMap<>();
    }

    public void addStoryToSprint(Ticket ticket) {
        tickets.putIfAbsent(ticket.getTitle(), ticket);
    }

    public void removeStoryFromSprint(Ticket ticket) {
        tickets.remove(ticket.getTitle());
    }

    public boolean isTicketPartOfSprint(Ticket ticket) {
        return tickets.containsKey(ticket.getTitle());
    }

}
