package org.example.sortingstrategy;

import org.example.model.Slot;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class SlotSortingByAvailability implements SlotSortingStrategy {
    @Override
    public List<Slot> sort(List<Slot> slots) {
        List<Slot> sortedSlots = new ArrayList<>(slots);
        sortedSlots.sort(Comparator.comparingInt(Slot::getAvailableSlots));
        return sortedSlots;
    }
}
