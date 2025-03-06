package org.example.sortingstrategy;

import org.springframework.stereotype.Component;

@Component
public class SortingStrategyFactory {
    SlotSortingByTime slotSortingByTime;
    SlotSortingByAvailability slotSortingByAvailability;

    public SortingStrategyFactory(SlotSortingByTime slotSortingByTime, SlotSortingByAvailability slotSortingByAvailability) {
        this.slotSortingByTime = slotSortingByTime;
        this.slotSortingByAvailability = slotSortingByAvailability;
    }

    public SlotSortingStrategy getSortingStrategy(String sortingType) {
        if (sortingType.equals("AVAILABILITY")) {
            return slotSortingByAvailability;
        } else if (sortingType.equals("TIME")) {
            return slotSortingByTime;
        }
        return null;
    }
}
