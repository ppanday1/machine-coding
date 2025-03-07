package org.example.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

@Data
@Slf4j
public class Slot implements WaitlistObservable {
    private String slotId;
    private String centerName;
    private int startTime;
    private int endTime;
    private int availableSlots;
    private Activity activity;
    private Queue<WaitlistObserver> waitingQueue;

    public Slot(String centerName, int startTime, int endTime, int availableSots, Activity activity) {
        this.slotId = UUID.randomUUID().toString();
        this.centerName = centerName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.availableSlots = availableSots;
        this.activity = activity;
        waitingQueue = new LinkedList<>();
    }

    public synchronized boolean bookSlot(User user) {
        if (availableSlots > 0) {
            availableSlots--;
            return true;
        } else {
            addObserver(user); //observer pattern to be implemented
        }
        return false;
    }

    public synchronized User releaseSlot() {
        availableSlots++;
        return notifyObserver();
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

    @Override
    public void addObserver(WaitlistObserver waitlistObserver) {
        waitingQueue.add(waitlistObserver);
    }

    @Override
    public User notifyObserver() {
        if (waitingQueue.size() > 0) {
            User user = (User) waitingQueue.poll();
            user.update(this);
            return user;
        }
        return null;
    }
}
