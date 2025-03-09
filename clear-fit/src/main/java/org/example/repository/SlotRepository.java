package org.example.repository;

import org.example.exception.EntityAlreadyFoundException;
import org.example.exception.EntityDoesNotExistException;
import org.example.model.Activity;
import org.example.model.Slot;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Repository
public class SlotRepository {
    private final ConcurrentHashMap<String, Slot> slots;
    private final Lock readLock;
    private final Lock writeLock;

    public SlotRepository() {
        slots = new ConcurrentHashMap<>();
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        readLock = reentrantReadWriteLock.readLock();
        writeLock = reentrantReadWriteLock.writeLock();
    }

    public void saveSlot(Slot slot) throws EntityAlreadyFoundException {
        writeLock.lock();
        try {
            if (slots.containsKey(slot.getSlotId())) {
                throw new EntityAlreadyFoundException("Slot with id " + slot.getSlotId() + " already found");
            }
            slots.put(slot.getSlotId(), slot);
        } finally {
            writeLock.unlock();
        }
    }

    public List<Slot> getAllSlotForCenter(String name) {
        readLock.lock();
        try {
            List<Slot> slotsForCenter = slots.values().
                    stream().
                    filter(e -> e.getCenterName().equals(name)).
                    toList();
            return new ArrayList<>(slotsForCenter);
        } finally {
            readLock.unlock();
        }
    }

    public Slot getSlotForCenterForTimingAndWorkoutType(String name, int start, int end, Activity activity) {
        readLock.lock();
        try {
            List<Slot> slotList = slots.values()
                    .stream()
                    .filter(e -> ((e.getStartTime() == start && e.getEndTime() == end)
                            && (e.getActivity().equals(activity) && e.getCenterName().equals(name))))
                    .toList();
            if (slotList.size() == 0) {
                return null;
            }
            return slotList.get(0);
        } finally {
            readLock.unlock();
        }
    }

    public Slot getSlotById(String id) throws EntityDoesNotExistException {
        readLock.lock();
        try {
            if (!slots.containsKey(id)) {
                throw new EntityDoesNotExistException("Slot doesn't exist with id " + id);
            }
            return slots.get(id);
        } finally {
            readLock.unlock();
        }
    }

    public List<Slot> getSlotByCenter(String centerName) {
        readLock.lock();
        try {
            return slots.values().stream().filter(e -> e.getCenterName().equals(centerName)).toList();
        } finally {
            readLock.unlock();
        }
    }

    public List<Slot> getSlotByActivity(Activity activity) {
        readLock.lock();
        try {
            return slots.values()
                    .stream()
                    .filter(e -> e.getActivity().equals(activity) && e.getAvailableSlots() > 0)
                    .toList();
        } finally {
            readLock.unlock();
        }
    }

    public List<Slot> getSlotByCenterAndWorkout(String centerName, Activity activity) {
        readLock.lock();
        try {
            return slots.values()
                    .stream()
                    .filter(e -> e.getCenterName().equals(centerName) && e.getActivity().equals(activity) && e.getAvailableSlots() > 0)
                    .toList();
        } finally {
            readLock.unlock();
        }
    }
}
