package org.example.repository;

import org.example.model.Slot;
import org.example.model.Speciality;
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
        for (String doctorName: doctors){
            result.addAll(getAvailableSlotForDoctor(doctorName));
        }
        return result;
    }

    public boolean bookSlotForDoctor(String doctorName, LocalTime time, String user) {
        for (Slot slot : slots.get(doctorName)) {
            if (slot.getLocalTime().equals(time) ) {
                if(slot.isBooked()){
                    slot.registerUser(user);
                }else{
                    slot.bookSlot();
                }
                return true;
            }
        }
        return false;
    }

    public boolean releaseSlotForDoctor(String doctorName, LocalTime time) {
        for (Slot slot : slots.get(doctorName)) {
            if (slot.getLocalTime().equals(time) && slot.isBooked()) {
                slot.releaseSlot();
                return true;
            }
        }
        return false;
    }


}
