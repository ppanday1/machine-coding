package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.repository.BookingRepository;
import org.example.repository.DoctorRepository;
import org.example.repository.SlotRepository;
import org.example.repository.UserRepository;
import org.example.service.BookingService;
import org.example.service.UserService;
import org.example.strategy.DefaultRankingStrategy;
import org.example.strategy.RankingStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalTime;
import java.util.List;

@SpringBootApplication
@Slf4j
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        SpringApplication.run(Main.class);
        DoctorRepository doctorRepository=new DoctorRepository();
        SlotRepository slotRepository=new SlotRepository();
        UserRepository userRepository=new UserRepository();
        BookingRepository bookingRepository=new BookingRepository();
        RankingStrategy rankingStrategy=new DefaultRankingStrategy();

        BookingService bookingService=new BookingService(doctorRepository,slotRepository,bookingRepository);
        UserService userService= new UserService(doctorRepository,slotRepository,rankingStrategy,bookingRepository);

        userService.registerDoctor("Doc","Cardiologist");
        userService.declareAvailability("Doc", List.of(LocalTime.of(1,30),LocalTime.of(2,0)));
        log.info("{}",userService.searchDoctor("Cardiologist"));
        bookingService.bookAppointment("Doc","Patient1",LocalTime.of(1,30));
        log.info("{}",userService.searchDoctor("Cardiologist"));
        bookingService.bookAppointment("Doc","Patient2",LocalTime.of(2,0));
        bookingService.cancelAppointment("Doc","Patient1",LocalTime.of(1,30));
    }
}