package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.model.Activity;
import org.example.model.Booking;
import org.example.model.Slot;
import org.example.model.User;
import org.example.repository.BookingRepository;
import org.example.repository.SlotRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final SlotRepository slotRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, SlotRepository slotRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.slotRepository = slotRepository;
    }

    public synchronized void addBooking(String userName, String centerName, String workoutType, int startTime, int endTime) {
        try {
            Activity activity = Activity.valueOf(workoutType);
            User user = userRepository.getUserByName(userName);
            Slot slot = slotRepository.getSlotForCenterForTimingAndWorkoutType(centerName, startTime, endTime, activity);
            if (!slot.bookSlot(user)) {
                log.error("Slot is not free");
                return;
            }
            Booking booking = new Booking(centerName, userName, startTime, endTime, slot.getSlotId(), activity);
            bookingRepository.saveBooking(booking);
        } catch (Exception e) {
            log.error("Error occurred while trying to book the session for user", e);
        }
    }

    public synchronized void cancelBooking(String userName, String centerName, String workOutType, int startTime, int endTime) {
        try {
            Activity activity = Activity.valueOf(workOutType);
            Booking booking = bookingRepository.getBookingFor(userName, centerName, activity, startTime, endTime);
            bookingRepository.deleteBooking(booking);
            Slot slot=slotRepository.getSlotById(booking.getSlotId());
            User notifiedUser=slot.releaseSlot();
        } catch (Exception e) {
            log.error("Exception occurred while trying to cancle booking ", e);
        }
    }
}
