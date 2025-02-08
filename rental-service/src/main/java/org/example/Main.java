package org.example;

import org.example.model.RentalController;
import org.example.model.RentalService;
import org.example.model.Vehicle;
import org.example.model.VehicleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        SpringApplication.run(Main.class, args);
        RentalService service = new RentalService();
        RentalController rentalController = new RentalController(service);


        rentalController.onboardBranch("Koramangala");
        rentalController.onboardBranch("jayanagar");
        rentalController.onboardBranch("malleshwaram");


        rentalController.onboardVehicle("Koramangala", "SUV", 12, 1);
        rentalController.onboardVehicle("Koramangala", "SEDAN", 10, 3);
        rentalController.onboardVehicle("Koramangala", "BIKE", 20, 3);


        rentalController.onboardVehicle("jayanagar", "SEDAN", 11, 3);
        rentalController.onboardVehicle("jayanagar", "BIKE", 30, 3);
        rentalController.onboardVehicle("jayanagar", "HATCHBACK", 8, 4);

        rentalController.onboardVehicle("malleshwaram", "SUV", 11, 1);
        rentalController.onboardVehicle("malleshwaram", "BIKE", 3, 10);
        rentalController.onboardVehicle("malleshwaram", "SEDAN", 10, 3);


//        rentalController.systemViewForTimeSlot(getLocalDateTime("20th Mar 10:00 PM"), getLocalDateTime("20th Mar 12:00 PM")); // should book from malleshwaram.

//        rentalController.onboardVehicle("koramangala","SEDAN",);//add_vehicle(‘koramangala’,  “1 sedan”);
        rentalController.rentVehicle("SUV", getLocalDateTime("20th Mar 08:00 PM"), getLocalDateTime("20th Mar 10:00 PM"));
        rentalController.rentVehicle("SUV", getLocalDateTime("20th Mar 08:00 PM"), getLocalDateTime("20th Mar 10:00 PM")); // should book from malleshwaram.
        rentalController.rentVehicle("SUV", getLocalDateTime("20th Mar 08:00 PM"), getLocalDateTime("20th Mar 10:00 PM")); // should book from malleshwaram.
        rentalController.systemViewForTimeSlot(getLocalDateTime("20th Mar 08:00 PM"), getLocalDateTime("20th Mar 09:00 PM")); // should book from malleshwaram.

    }

    private static LocalDateTime getLocalDateTime(String input) {
        int currentYear = Year.now().getValue();
        input = input + " " + currentYear;

        // Define formatter to handle 'st', 'nd', 'rd', 'th' in day
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("d['st']['nd']['rd']['th'] MMM h:mm a yyyy")
                .toFormatter(Locale.ENGLISH);

        // Parse the input string into LocalDateTime
        return LocalDateTime.parse(input, formatter);

    }
}