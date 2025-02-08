package org.example.repository;

import org.example.model.Slot;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class SlotRepository {
    HashMap<String, List<Slot>> slots;

    public SlotRepository() {
        slots = new HashMap<>();
    }

    public boolean addSlotForDoctor(String name, List<LocalTime> times) {
        List<Slot> slotsForDoctor = slots.getOrDefault(name, new ArrayList<>());
        for (LocalTime time : times) {
            slotsForDoctor.add(new Slot(name, time));
        }
        slots.put(name, slotsForDoctor);
        return true;
    }

    public List<Slot> getAvailableSlotForDoctor(String name) {
        List<Slot> result = new ArrayList<>();
        for (Slot slot : slots.getOrDefault(name, new ArrayList<>())) {
            if (!slot.isBooked()) {
                result.add(slot);
            }
        }
        return result;
    }

    public List<Slot> getAvailableSlotForDoctors(List<String> doctors) {
        List<Slot> result = new ArrayList<>();
        for (String doctorName : doctors) {
            result.addAll(getAvailableSlotForDoctor(doctorName));
        }
        return result;
    }

    public boolean bookSlotForDoctor(String doctorName, LocalTime time, String user) {
        for (Slot slot : slots.getOrDefault(doctorName, new ArrayList<>())) {
            if (slot.getLocalTime().equals(time)) {
                if (slot.isBooked()) {
                    slot.waitListPatient(user);
                } else {
                    slot.bookSlot();
                }
                return true;
            }
        }
        return false;
    }

    public String releaseSlotForDoctor(String doctorName, LocalTime time) {
        for (Slot slot : slots.getOrDefault(doctorName, new ArrayList<>())) {
            if (slot.getLocalTime().equals(time) && slot.isBooked()) {
                return slot.releaseSlot();
            }
        }
        return "";
    }


}
