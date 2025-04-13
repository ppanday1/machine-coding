package org.example.service;

import org.example.repository.CommentRepository;
import org.example.repository.SubTaskRepository;
import org.example.repository.TicketRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    //TO:DO
    private final CommentRepository commentRepository;
    private final TicketRepository ticketRepository;
    private final SubTaskRepository subTaskRepository;

    public CommentService(CommentRepository commentRepository, TicketRepository ticketRepository, SubTaskRepository subTaskRepository) {
        this.commentRepository = commentRepository;
        this.ticketRepository = ticketRepository;
        this.subTaskRepository = subTaskRepository;
    }
}
