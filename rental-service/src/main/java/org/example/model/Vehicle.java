package org.example.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.TreeMap;

@Data
public class Vehicle {
    private String id;
    private VehicleType vehicleType;
    private TreeMap<LocalDateTime, LocalDateTime> bookingDetails;
    private String branchName;

    public Vehicle(VehicleType vehicleType, String branchName) {
        this.vehicleType = vehicleType;
        bookingDetails = new TreeMap<>();
        this.branchName = branchName;
    }

    public boolean isAvailable(LocalDateTime start, LocalDateTime end) {
        while (!bookingDetails.isEmpty() && bookingDetails.firstKey().isBefore(LocalDateTime.now())) {
            bookingDetails.pollFirstEntry(); // More efficient than remove(firstKey)
        }

        LocalDateTime floor = bookingDetails.floorKey(start);
        LocalDateTime ceil = bookingDetails.ceilingKey(start);

        return (floor == null || bookingDetails.get(floor).isBefore(start))
                && (ceil == null || ceil.isAfter(end));
    }

    public void bookSlot(LocalDateTime start, LocalDateTime end) {
//        System.out.println("put somehitng "+start+ " "+end);
        bookingDetails.put(start, end);
    }
}
