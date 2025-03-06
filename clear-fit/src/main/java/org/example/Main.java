package org.example;

import org.example.repository.*;
import org.example.service.BookingService;
import org.example.service.CenterService;
import org.example.service.UserService;
import org.example.sortingstrategy.SlotSortingByAvailability;
import org.example.sortingstrategy.SlotSortingByTime;
import org.example.sortingstrategy.SortingStrategyFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
        SlotSortingByAvailability slotSortingByAvailability = new SlotSortingByAvailability();
        SlotSortingByTime slotSortingByTime = new SlotSortingByTime();
        SortingStrategyFactory sortingStrategyFactory = new SortingStrategyFactory(slotSortingByTime, slotSortingByAvailability);

        BookingRepository bookingRepository = new BookingRepository();
        CenterRepository centerRepository = new CenterRepository();
        CenterDetailRepository centerDetailRepository = new CenterDetailRepository();
        SlotRepository slotRepository = new SlotRepository();
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        BookingService bookingService = new BookingService(bookingRepository, userRepository, slotRepository);
        CenterService centerService = new CenterService(centerDetailRepository, centerRepository, slotRepository, sortingStrategyFactory);

//        centerService.addCenter("Kor");
//        Pair<Integer, Integer> time = Pair.of(6, 9);
//        Pair<Integer, Integer> time2 = Pair.of(18, 21);
//        centerService.addCenterTiming("Kor", List.of(time, time2));
////        centerService.viewWorkoutAvailability("Kor");
//
//        centerService.addActivity("Kor", List.of("WEIGHTS", "YOGA", "SWIMMING", "CARDIO"));
//
//
//        centerService.addCenter("BEL");
//        time = Pair.of(7, 10);
//        time2 = Pair.of(19, 22);
//        centerService.addCenterTiming("BEL", List.of(time, time2));
//        centerService.addActivity("BEL", List.of("WEIGHTS", "YOGA", "CARDIO"));
//        centerService.viewWorkoutAvailability("BEL");
//
//        centerService.addCenterWorkout("Kor", "WEIGHTS", 6, 7, 100);
//        centerService.addCenterWorkout("Kor", "CARDIO", 7, 8, 150);
//        centerService.addCenterWorkout("Kor", "YOGA", 8, 9, 200);
//        centerService.viewWorkoutAvailability("Kor");
//
//        centerService.addCenterWorkout("BEL", "WEIGHTS", 18, 19, 100);
//        centerService.addCenterWorkout("BEL", "SWIMMING", 19, 20, 100);
//        centerService.addCenterWorkout("BEL", "CARDIO", 19, 20, 20);
//        centerService.addCenterWorkout("BEL", "WEIGHTS", 20, 21, 100);
//        centerService.addCenterWorkout("BEL", "WEIGHTS", 21, 22, 100);
//        centerService.viewWorkoutAvailability("BEL");
//
//        userService.addUser("PRASHANT");
//        bookingService.addBooking("PRASHANT", "Kor", "WEIGHTS", 6, 7);
//        centerService.viewWorkoutAvailability("Kor", "WEIGHTS");
//        bookingService.cancelBooking("PRASHANT", "Kor", "WEIGHTS", 6, 7);
//        centerService.viewWorkoutAvailability("Kor", "SWIMMING");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] commands = input.split(" ");
        while (!commands[0].equals("EXIT")) {
            switch (commands[0]) {
                case "REGISTER" -> {
                    String userId = commands[1];
                    userService.addUser(userId);
                }
                case "ADD_CENTER" -> {
                    String centerName = commands[1];
                    centerService.addCenter(centerName);
                    break;
                }
                case "ADD_CENTER_TIMING" -> {
                    String centerName = commands[1];
                    List<Pair<Integer, Integer>> timing = new ArrayList<>();
                    int n = commands.length;
                    for (int i = 2; i < n; i += 2) {
                        timing.add(Pair.of(Integer.parseInt(commands[i]), Integer.parseInt(commands[i + 1])));
                    }
                    centerService.addCenterTiming(centerName, timing);
                    break;
                }
                case "ADD_ACTIVITY" -> {
                    String centerName = commands[1];
                    List<String> activities = new ArrayList<>(Arrays.asList(commands).subList(2, commands.length));
                    centerService.addActivity(centerName, activities);
                    break;
                }
                case "ADD_CENTER_WORKOUT" -> {
                    String centerName = commands[1];
                    String woroutType = commands[2];
                    int start = Integer.parseInt(commands[3]);
                    int end = Integer.parseInt(commands[4]);
                    int slots = Integer.parseInt(commands[5]);
                    centerService.addCenterWorkout(centerName, woroutType, start, end, slots);
                    break;
                }
                case "VIEW_WORKOUT" -> {
                    int n = commands.length;
                    if (n == 2) {
                        String workoutType = commands[1];
                        centerService.viewWorkoutAvailability(workoutType);
                    } else {
                        String centerName = commands[1];
                        String workoutType = commands[2];
                        centerService.viewWorkoutAvailability(centerName, workoutType);
                    }
                    break;
                }
                case "BOOK_SESSION" -> {
                    String userName = commands[1];
                    String centerName = commands[2];
                    String workoutType = commands[3];
                    int start = Integer.parseInt(commands[4]);
                    int end = Integer.parseInt(commands[5]);
                    bookingService.addBooking(userName, centerName, workoutType, start, end);
                    break;
                }
                case "CANCEL_SESSION" -> {
                    String userName = commands[1];
                    String centerName = commands[2];
                    String workoutType = commands[3];
                    int start = Integer.parseInt(commands[4]);
                    int end = Integer.parseInt(commands[5]);
                    bookingService.cancelBooking(userName, centerName, workoutType, start, end);
                    break;
                }
                default -> {
                    System.out.println("Wrong input command ");
                }
            }
            input = scanner.nextLine();
            commands = input.split(" ");
        }
    }
}