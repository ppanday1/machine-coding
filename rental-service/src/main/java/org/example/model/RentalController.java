package org.example.model;

import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class RentalController {
    RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    public void onboardBranch(String name) {
        rentalService.onboardBranch(name);
    }

    public void onboardVehicle(String branchName, String type, double price, int count) {
        rentalService.onboardVehicle(branchName, type, price, count);
    }

    public void rentVehicle(String type, LocalDateTime startTime, LocalDateTime endTime) {
        Vehicle rentedVehicle = rentalService.rentVehicle(type, startTime, endTime);
        if (rentedVehicle != null) {
            System.out.println("Successfully rented a " + type + " from " + rentedVehicle.getBranchName() + " " + rentedVehicle.isAvailable(startTime, endTime));
        } else {
            System.out.println("No " + type + " available for rent in requested time slot.");
        }
    }

    public void systemViewForTimeSlot(LocalDateTime startTime, LocalDateTime endTime) {
        rentalService.systemViewForTimeSlot(startTime, endTime);
    }
}
