package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@Slf4j
public class User implements WaitlistObserver {
    private String name;

    @Override
    public void update(Slot slot) {
        System.out.println("Notification to " + name + ": Slot available at " + slot.getCenterName() +
                " for " + slot.getActivity() + " from " + slot.getStartTime() + " to " + slot.getEndTime() +
                " with " + slot.getAvailableSlots() + " seats.");
    }
}
