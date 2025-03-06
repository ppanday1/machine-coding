package org.example.sortingstrategy;

import org.example.model.Slot;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SlotSortingByTime implements SlotSortingStrategy {
    @Override
    public List<Slot> sort(List<Slot> slots) {
        List<Slot> sortedSlots = new ArrayList<>(slots);
        sortedSlots.sort(Comparator.comparingInt(Slot::getStartTime));
        return sortedSlots;
    }
}
