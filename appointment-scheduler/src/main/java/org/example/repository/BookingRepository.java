package org.example.repository;

import org.example.model.Booking;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Repository
public class BookingRepository {
    HashSet<Booking> bookings;

    public BookingRepository() {
        this.bookings = new HashSet<>();
    }

    public boolean addBooking(Booking booking) {
        bookings.add(booking);
        return true;
    }

    public List<Booking> getBookingByPatient(String name) {
        List<Booking> bookingsForPatient = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getPatientName().equals(name)) {
                bookingsForPatient.add(booking);
            }
        }
        return bookingsForPatient;
    }

    public List<Booking> getBookingByDoctor(String name) {
        List<Booking> bookingsForDoctor = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getDoctorName().equals(name)) {
                bookingsForDoctor.add(booking);
            }
        }
        return bookingsForDoctor;
    }

    public boolean removeBooking(Booking booking){
        return bookings.remove(booking);
    }


}
