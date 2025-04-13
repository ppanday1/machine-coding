package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.config.WorkFlow;
import org.example.exception.TicketNotFoundException;
import org.example.exception.TicketWorkFlowStatusException;
import org.example.model.SubTask;
import org.example.model.Ticket;
import org.example.repository.SubTaskRepository;
import org.example.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class SubTaskService {
    private final SubTaskRepository subTaskRepository;
    private final TicketRepository ticketRepository;
    private final ConcurrentHashMap<String, ReentrantLock> locks;

    public SubTaskService(SubTaskRepository subTaskRepository,
                          TicketRepository ticketRepository) {
        this.subTaskRepository = subTaskRepository;
        this.ticketRepository = ticketRepository;
        this.locks = new ConcurrentHashMap<>();
    }

    public void addSubTask(String title, String parentTask) {
        ReentrantLock lock = locks.computeIfAbsent(title, k -> new ReentrantLock());
        lock.lock();
        try {
            Ticket ticket = ticketRepository.getTicketByName(parentTask);
            if (ticket == null) {
                throw new TicketNotFoundException("Parent ticket with id not found " + parentTask);
            }
            if (subTaskRepository.subTaskExist(title)) {
                throw new Exception("Subtask already exist " + title);
            }
            SubTask subTask = new SubTask(title, parentTask, ticket.getWorkFlows());
            subTaskRepository.addSubtask(subTask);
        } catch (Exception e) {
            log.error("Exception occurred while trying to add subtask ", e);
        } finally {
            lock.unlock();
        }
    }

    public void updateSubTaskStatus(String title, String nextWorkFlow) {
        ReentrantLock lock = locks.computeIfAbsent(title, k -> new ReentrantLock());
        lock.lock();
        try {
            SubTask subTask = subTaskRepository.getSubTask(title);
            if (subTask == null) {
                throw new Exception("Subtask not found with id " + title);
            }
            if (!subTask.getNExtWorkFlow().equals(WorkFlow.valueOf(nextWorkFlow))) {
                throw new TicketWorkFlowStatusException("Not correct next workflow status " + nextWorkFlow);
            }
            subTask.setWorkFlowToNext();
        } catch (Exception e) {
            log.error("Exception occurred while trying to update workflow");
        } finally {
            lock.unlock();
        }
    }

    public void deleteSubTask(String title) {
        ReentrantLock lock = locks.computeIfAbsent(title, k -> new ReentrantLock());
        lock.lock();
        try {
            SubTask subTask = subTaskRepository.getSubTask(title);
            if (subTask == null) {
                throw new TicketNotFoundException("SubTask now found " + title);
            }
            subTaskRepository.deleteSubTask(title);
        } catch (Exception e) {
            log.error("Exception occurred while trying to delete ", e);
        } finally {
            lock.unlock();
        }
    }
}
