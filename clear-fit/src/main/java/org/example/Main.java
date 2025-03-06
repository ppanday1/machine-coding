package org.example;

import org.example.repository.*;
import org.example.service.BookingService;
import org.example.service.CenterService;
import org.example.service.UserService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.util.Pair;

import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
//        Scanner scanner = new Scanner(System.in);
//        String comman = scanner.nextLine();
//        System.out.println(comman);
//        LocalDateTime dateTime=LocalDateTime.parse(comman, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//        System.out.println(dateTime);
//        SpringApplication.run(Main.class, args);

        BookingRepository bookingRepository = new BookingRepository();
        CenterRepository centerRepository = new CenterRepository();
        CenterDetailRepository centerDetailRepository = new CenterDetailRepository();
        SlotRepository slotRepository = new SlotRepository();
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        BookingService bookingService = new BookingService(bookingRepository, userRepository, slotRepository);
        CenterService centerService = new CenterService(centerDetailRepository, centerRepository, slotRepository);

        centerService.addCenter("Kor");
        Pair<Integer, Integer> time = Pair.of(6, 9);
        Pair<Integer, Integer> time2 = Pair.of(18, 21);
        centerService.addCenterTiming("Kor", List.of(time, time2));
//        centerService.viewWorkoutAvailability("Kor");

        centerService.addActivity("Kor", List.of("WEIGHTS", "YOGA", "SWIMMING", "CARDIO"));


        centerService.addCenter("BEL");
        time = Pair.of(7, 10);
        time2 = Pair.of(19, 22);
        centerService.addCenterTiming("BEL", List.of(time, time2));
        centerService.addActivity("BEL", List.of("WEIGHTS", "YOGA", "CARDIO"));
        centerService.viewWorkoutAvailability("BEL");

        centerService.addCenterWorkout("Kor", "WEIGHTS", 6, 7, 100);
        centerService.addCenterWorkout("Kor", "CARDIO", 7, 8, 150);
        centerService.addCenterWorkout("Kor", "YOGA", 8, 9, 200);
        centerService.viewWorkoutAvailability("Kor");

        centerService.addCenterWorkout("BEL", "WEIGHTS", 18, 19, 100);
        centerService.addCenterWorkout("BEL", "SWIMMING", 19, 20, 100);
        centerService.addCenterWorkout("BEL", "CARDIO", 19, 20, 20);
        centerService.addCenterWorkout("BEL", "WEIGHTS", 20, 21, 100);
        centerService.addCenterWorkout("BEL", "WEIGHTS", 21, 22, 100);
        centerService.viewWorkoutAvailability("BEL");

        userService.addUser("PRASHANT");
        bookingService.addBooking("PRASHANT", "Kor", "WEIGHTS", 6, 7);
        centerService.viewWorkoutAvailability("Kor", "WEIGHTS");
        bookingService.cancelBooking("PRASHANT", "Kor", "WEIGHTS", 6, 7);
        centerService.viewWorkoutAvailability("Kor", "SWIMMING");

    }
}