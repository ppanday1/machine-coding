package org.example.service;

import org.example.model.Branch;
import org.example.model.Vehicle;
import org.example.model.VehicleType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class RentalService {
    HashMap<String, Branch> branches;

    public RentalService() {
        branches = new HashMap<>();
    }

    public boolean onboardBranch(String name) {
        if (branches.containsKey(name)) {
            return false;
        }
        branches.put(name, new Branch(name));
        return true;
    }

    public boolean onboardVehicle(String branchName, String vehicleType, double price, int count) {
        if (!branches.containsKey(branchName)) {
            return false;
        }
        VehicleType type = VehicleType.valueOf(vehicleType);
        Branch branch = branches.get(branchName);
        branch.addVehicle(type, count, price);
        return true;
    }

    public void systemViewForTimeSlot(LocalDateTime startTime, LocalDateTime endTime) {
        for (Branch branch : branches.values()) {
            System.out.println("'" + branch.getName() + "':");
            for (VehicleType type : branch.getVehicles().keySet()) {
                int available = branch.availableCount(type, startTime, endTime);
                if (available > 0) {
                    System.out.println("  '" + type + "' is available for Rs." + branch.getPrice(type) + " count is " + available);
                } else {
                    System.out.println("  All '" + type + "' are booked.");
                }
            }
        }
    }

    public Vehicle rentVehicle(String vehicleType, LocalDateTime startTime, LocalDateTime endTime) {
        Branch bestBranch = null;
        double minPrice = Double.MAX_VALUE;
        VehicleType type = VehicleType.valueOf(vehicleType);
        for (Branch branch : branches.values()) {
            if (branch.availableCount(type, startTime, endTime) > 0 && branch.getPrice(type) < minPrice && branch.isBranchActive()) {
                minPrice = branch.getPrice(type);
                bestBranch = branch;
            }
        }

        if (bestBranch != null) {
            return bestBranch.rentVehicle(type, startTime, endTime);
        }
        return null;
    }
}
