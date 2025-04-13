package org.example.factory;

import org.example.config.TicketConfig;
import org.example.config.TicketType;
import org.example.exception.TicketTypeNotFoundException;
import org.example.model.Ticket;
import org.example.repository.TicketConfigRepository;
import org.springframework.stereotype.Component;

@Component
public class TicketFactory {
    private TicketConfigRepository ticketConfigRepository;

    public TicketFactory(TicketConfigRepository ticketConfigRepository) {
        this.ticketConfigRepository = ticketConfigRepository;
    }

    public Ticket getTicket(TicketType type, String name) throws TicketTypeNotFoundException {
        TicketConfig config = ticketConfigRepository.getTicketConfig(type);
        if (config == null) {
            throw new TicketTypeNotFoundException("Ticket type " + type + " not found");
        }
        return new Ticket(name, config, type);
    }


}
