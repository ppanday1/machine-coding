package org.example.sortingstrategy;

import org.example.model.Slot;

import java.util.List;

public interface SlotSortingStrategy {
    public List<Slot> sort(List<Slot> slots);
}
