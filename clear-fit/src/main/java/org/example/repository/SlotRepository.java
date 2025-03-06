package org.example.repository;

import org.example.exception.EntityAlreadyFoundException;
import org.example.exception.EntityDoesNotExistException;
import org.example.model.Activity;
import org.example.model.Slot;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SlotRepository {
    private ConcurrentHashMap<String, Slot> slots;

    public SlotRepository() {
        slots = new ConcurrentHashMap<>();
    }

    public void saveSlot(Slot slot) throws EntityAlreadyFoundException {
        if (slots.containsKey(slot.getSlotId())) {
            throw new EntityAlreadyFoundException("Slot with id " + slot.getSlotId() + " already found");
        }
        slots.put(slot.getSlotId(), slot);
    }

    public List<Slot> getAllSlotForCenter(String name) {
        List<Slot> slotsForCenter = slots.values().
                stream().
                filter(e -> e.getCenterName().equals(name)).
                toList();
        return new ArrayList<>(slotsForCenter);
    }

    public Slot getSlotForCenterForTimingAndWorkoutType(String name, int start, int end, Activity activity) {
        List<Slot> slotList = slots.values()
                .stream()
                .filter(e -> ((e.getStartTime() == start && e.getEndTime() == end)
                        && (e.getActivity().equals(activity) && e.getCenterName().equals(name))))
                .toList();
        if (slotList.size() == 0) {
            return null;
        }
        return slotList.get(0);
    }

    public Slot getSlotById(String id) throws EntityDoesNotExistException {
        if (!slots.containsKey(id)) {
            throw new EntityDoesNotExistException("Slot doesn't exist with id " + id);
        }
        return slots.get(id);
    }

    public List<Slot> getSlotByCenter(String centerName) {
        return slots.values().stream().filter(e -> e.getCenterName().equals(centerName)).toList();
    }

    public List<Slot> getSlotByCenterAndWorkout(String centerName, Activity activity) {
        return slots.values().stream().filter(e -> e.getCenterName().equals(centerName) && e.getActivity().equals(activity)).toList();
    }
}
