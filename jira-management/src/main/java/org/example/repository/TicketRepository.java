package org.example.repository;

import org.example.model.Ticket;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TicketRepository {
    private final ConcurrentHashMap<String, Ticket> tickets;

    public TicketRepository() {
        this.tickets = new ConcurrentHashMap<>();
    }

    public Ticket getTicketByName(String title) {
        return tickets.get(title);
    }

    public void addTicket(Ticket ticket) {
        tickets.put(ticket.getTitle(), ticket);
    }

    public boolean ticketExist(String title) {
        return tickets.containsKey(title);
    }
}
