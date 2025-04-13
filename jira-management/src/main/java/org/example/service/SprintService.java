package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.config.TicketType;
import org.example.exception.TicketNotFoundException;
import org.example.model.Ticket;
import org.example.repository.SprintRepository;
import org.example.repository.TicketRepository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class SprintService {
    private final TicketRepository ticketRepository;
    private final SprintRepository sprintRepository;

    private final ConcurrentHashMap<String, ReentrantLock> locks;

    public SprintService(TicketRepository ticketRepository, SprintRepository sprintRepository) {
        this.ticketRepository = ticketRepository;
        this.sprintRepository = sprintRepository;
        this.locks = new ConcurrentHashMap<>();
    }

    public void addTicketToSprint(String title) {
        ReentrantLock lock = locks.computeIfAbsent(title, k -> new ReentrantLock());
        lock.lock();
        try {
            Ticket ticket = ticketRepository.getTicketByName(title);
            if (ticket == null) {
                throw new TicketNotFoundException("Ticket with name " + title + " not found");
            }
            if (!ticket.getType().equals(TicketType.STORY)) {
                throw new Exception("Ticket is not of type story");
            }
            sprintRepository.addStoryToSprint(ticket);
        } catch (Exception e) {
            log.error("Error occurred with adding ticket to sprint", e);
        } finally {
            lock.unlock();
        }
    }


    public void removeTicketFromSprint(String title) {
        ReentrantLock lock = locks.computeIfAbsent(title, k -> new ReentrantLock());
        lock.lock();
        try {
            Ticket ticket = ticketRepository.getTicketByName(title);
            if (ticket == null) {
                throw new TicketNotFoundException("Ticket with name " + title + " not found");
            }
            if (!ticket.getType().equals(TicketType.STORY)) {
                throw new Exception("Ticket is not of type story");
            }
            if (!sprintRepository.isTicketPartOfSprint(ticket)) {
                throw new Exception("Ticket not part of sprint " + title);
            }
            sprintRepository.removeStoryFromSprint(ticket);
        } catch (Exception e) {
            log.error("Error occurred with adding ticket to sprint", e);
        } finally {
            lock.unlock();
        }
    }
}
