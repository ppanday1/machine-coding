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

    public SlotSortingStrategy getSortingStrategy(SortingParam sortingType) {
        if (sortingType.equals(SortingParam.AVAILABLE_SLOT)) {
            return slotSortingByAvailability;
        } else if (sortingType.equals(SortingParam.TIME)) {
            return slotSortingByTime;
        }
        return null;
    }
}
