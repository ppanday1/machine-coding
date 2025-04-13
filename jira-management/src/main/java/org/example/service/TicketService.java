package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.config.TicketType;
import org.example.config.WorkFlow;
import org.example.exception.TicketAlreadyExistsException;
import org.example.exception.TicketNotFoundException;
import org.example.exception.TicketWorkFlowStatusException;
import org.example.factory.TicketFactory;
import org.example.model.Ticket;
import org.example.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketFactory ticketFactory;
    private final ConcurrentHashMap<String, ReentrantLock> locks;

    public TicketService(TicketRepository ticketRepository,
                         TicketFactory ticketFactory) {
        this.ticketRepository = ticketRepository;
        this.ticketFactory = ticketFactory;
        this.locks = new ConcurrentHashMap<>();
    }

    public Ticket createTicket(String type, String title) {
        ReentrantLock lock = locks.computeIfAbsent(title, k -> new ReentrantLock());
        lock.lock();
        Ticket ticket = null;
        try {
            TicketType ticketType = TicketType.valueOf(type);
            if (ticketRepository.ticketExist(title)) {
                throw new TicketAlreadyExistsException("Ticket with title " + title + " already Exists");
            }
            ticket = ticketFactory.getTicket(ticketType, title);
            ticketRepository.addTicket(ticket);
        } catch (Exception e) {
            log.error("Exception occurred ", e);
        } finally {
            lock.unlock();
        }
        return ticket;
    }

    public void updateTicketStatus(String title, String newStatus) {
        ReentrantLock lock = locks.computeIfAbsent(title, k -> new ReentrantLock());
        lock.lock();
        try {
            Ticket ticket = ticketRepository.getTicketByName(title);
            if (ticket == null) {
                throw new TicketNotFoundException("ticket with title " + title + " not found");
            }
            if (!ticket.getNextWorkFlow().equals(WorkFlow.valueOf(newStatus))) {
                throw new TicketWorkFlowStatusException("Ticket is not in correct new status- " + newStatus);
            }
            ticket.setWorkFlowToNext();
        } catch (Exception e) {
            log.error("Exception occurred while trying to update status ", e);
        } finally {
            lock.unlock();
        }
    }
}
