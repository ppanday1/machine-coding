package org.example.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

@Data
@Slf4j
public class Slot {
    private String slotId;
    private String centerName;
    private int startTime;
    private int endTime;
    private int availableSlots;
    private Activity activity;
    private Queue<String> waitingQueue;

    public Slot(String centerName, int startTime, int endTime, int availableSots, Activity activity) {
        this.slotId = UUID.randomUUID().toString();
        this.centerName = centerName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.availableSlots = availableSots;
        this.activity = activity;
        waitingQueue = new LinkedList<>();
    }

    public synchronized boolean bookSlot(String name) {
        if (availableSlots > 0) {
            availableSlots--;
            return true;
        } else {
            waitingQueue.add(name); //observer pattern to be implemented
        }
        return false;
    }

    public synchronized void releaseSlot() {
        availableSlots++;
        if(waitingQueue.size()!=0){
            log.info("all queued user notified {}", waitingQueue);
            waitingQueue.clear();
        }
    }

    private void notifyUser(){
        
    }

    @Override
    public String toString() {
        return "Slot{" +
                "centerName='" + centerName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", availableSlots=" + availableSlots +
                ", activity=" + activity +
                '}';
    }
}
