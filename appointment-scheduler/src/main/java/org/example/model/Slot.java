package org.example.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

@Data
@Slf4j
public class Slot {
    private String doctorName;
    private LocalTime localTime;
    private boolean isBooked;

    private Queue<String> userQueue;

    public Slot(String doctorName, LocalTime localTime) {
        this.doctorName = doctorName;
        this.localTime = localTime;
        this.isBooked = false;
        userQueue = new LinkedList<>();
    }

    public void bookSlot() {
        isBooked = true;
    }

    public void releaseSlot() {
        isBooked = false;
        notifyUser();
    }

    public void notifyUser() {
        log.info("{} user notified", userQueue.poll());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slot slot = (Slot) o;
        return doctorName.equals(slot.doctorName) && localTime.equals(slot.localTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctorName, localTime);
    }

    public void registerUser(String user) {
        userQueue.add(user);
    }
}
