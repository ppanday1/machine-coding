package org.example.repository;

import org.example.exception.EntityDoesNotExistException;
import org.example.model.Activity;
import org.example.model.Booking;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BookingRepository {
    private final ConcurrentHashMap<String, Booking> bookings;

    public BookingRepository() {
        this.bookings = new ConcurrentHashMap<>();
    }

    public synchronized List<Booking> getBookingForUser(String userName) throws EntityDoesNotExistException {
        List<Booking> bookingForUser = bookings.values().stream().filter(e -> e.getUserName().equals(userName)).toList();
        return new ArrayList<>(bookingForUser);
    }

    public synchronized void saveBooking(Booking booking) {
        bookings.put(booking.getBookingId(), booking);
    }

    public synchronized Booking getBookingFor(String userName, String centerName, Activity activity, int startTime, int endTime) throws EntityDoesNotExistException {
        List<Booking> allBookings = bookings.values()
                .stream()
                .filter(e -> ((e.getUserName().equals(userName) && e.getCenterName().equals(centerName)
                        && (e.getStartTime() == startTime && e.getEndTime() == endTime) &&
                        e.getActivity().equals(activity)))).toList();
        if (allBookings.size() == 0) {
            throw new EntityDoesNotExistException("Booking doesn't exist");
        }
        return allBookings.get(0);
    }

    public synchronized void deleteBooking(Booking booking) {
        bookings.remove(booking.getBookingId());
    }
}
