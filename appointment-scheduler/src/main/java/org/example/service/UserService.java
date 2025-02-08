package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.model.Doctor;
import org.example.model.Slot;
import org.example.model.Speciality;
import org.example.repository.BookingRepository;
import org.example.repository.DoctorRepository;
import org.example.repository.SlotRepository;
import org.example.strategy.RankingStrategy;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
public class UserService {

    private DoctorRepository doctorRepository;
    private SlotRepository slotRepository;
    private BookingRepository bookingRepository;

    private RankingStrategy rankingStrategy;

    public UserService(DoctorRepository doctorRepository,
                       SlotRepository slotRepository,
                       RankingStrategy rankingStrategy,
                       BookingRepository bookingRepository) {
        this.doctorRepository = doctorRepository;
        this.slotRepository = slotRepository;
        this.rankingStrategy = rankingStrategy;
        this.bookingRepository = bookingRepository;
    }

    public void setRankingStrategy(RankingStrategy rankingStrategy) {
        this.rankingStrategy = rankingStrategy;
    }

    public synchronized boolean registerDoctor(String name, String speciality) {
        Speciality sp = Speciality.valueOf(speciality);
        return doctorRepository.addDoctor(name, sp);
    }

    public boolean declareAvailability(String name, List<LocalTime> times) {
        if (doctorRepository.getDoctor(name) == null) {
            log.info("Doctor doesn't exist {}", name);
        }
        HashSet<LocalTime> uniqueSlots = new HashSet<>(times);
        if (uniqueSlots.size() != times.size()) {
            log.error("Slots provided are not unique");
            return false;
        }
        LocalTime start = LocalTime.of(9, 0);  // 9:00 AM
        LocalTime end = LocalTime.of(21, 0);   // 9:00 PM

        if(times.stream().allMatch(time -> !time.isBefore(start) && !time.isAfter(end))){
            return slotRepository.addSlotForDoctor(name, times);
        };
        return false;
    }

    public List<Slot> searchDoctor(String spec) {
        List<String> doctors = doctorRepository.findDoctorForSpeciality(Speciality.valueOf(spec))
                .stream().
                map(Doctor::getName)
                .toList();
        List<Slot> availableSlots = slotRepository.getAvailableSlotForDoctors(doctors);
        return rankingStrategy.getRanking(availableSlots);
    }

    public void getBookingForPatient(String user) {
        log.info("Booking for user is {} ", bookingRepository.getBookingByPatient(user));
    }

    public void getBookingForDoctor(String user) {
        log.info("Booking for user is {} ", bookingRepository.getBookingByDoctor(user));
    }
}
