package org.example.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Slf4j
public class Branch {
    private String name;
    private HashMap<VehicleType, List<Vehicle>> vehicles;
    private HashMap<VehicleType, Double> pricing;
    private boolean isActive;

    public Branch(String name) {
        vehicles = new HashMap<>();
        pricing = new HashMap<>();
        isActive = true;
        this.name = name;
    }

    public void addVehicle(VehicleType type, int count, double price) {
        if (!isActive) {
            return;
        }
        List<Vehicle> vehicleForType = vehicles.getOrDefault(type, new ArrayList<>());
        for (int i = 0; i < count; i++) {
            vehicleForType.add(new Vehicle(type, name));
        }
        vehicles.put(type, vehicleForType);
        pricing.put(type, price);
    }

    public boolean isAvailable(VehicleType type, LocalDateTime start, LocalDateTime end) {
        List<Vehicle> vehiclesForType = vehicles.get(type);
        for (Vehicle vehicle : vehiclesForType) {
            if (vehicle.isAvailable(start, end)) {
                return true;
            }
        }
        return false;
    }

    public int availableCount(VehicleType type, LocalDateTime startTime, LocalDateTime endTime) {
        return (int) vehicles.getOrDefault(type, new ArrayList<>())
                .stream().filter(v -> v.isAvailable(startTime, endTime)).count();
    }

    public Vehicle rentVehicle(VehicleType type, LocalDateTime start, LocalDateTime end) {

        if (!vehicles.containsKey(type) || !isActive) return null;

        for (Vehicle vehicle : vehicles.get(type)) {
            if (vehicle.isAvailable(start, end)) {
                vehicle.bookSlot(start, end);
                return vehicle;
            }
        }
        return null;
    }

    public void enableBranch() {
        isActive = true;
    }

    public void disable() {
        isActive = false;
    }

    public double getPrice(VehicleType type) {
        return pricing.get(type);
    }

    public boolean isBranchActive() {
        return isActive;
    }


}
