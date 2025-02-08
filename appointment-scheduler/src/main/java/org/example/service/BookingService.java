package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.model.Booking;
import org.example.model.Slot;
import org.example.repository.BookingRepository;
import org.example.repository.DoctorRepository;
import org.example.repository.SlotRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookingService {
    private DoctorRepository doctorRepository;
    private SlotRepository slotRepository;
    private BookingRepository bookingRepository;

    public BookingService(DoctorRepository doctorRepository,
                          SlotRepository slotRepository,
                          BookingRepository bookingRepository) {
        this.doctorRepository = doctorRepository;
        this.slotRepository = slotRepository;
        this.bookingRepository = bookingRepository;
    }

    public synchronized boolean bookAppointment(String doctorName, String patientName, LocalTime time) {

        if (doctorRepository.getUser(doctorName) == null) {
            return false;
        }
        //Patient is not free
        List<Booking> bookingForPatientAtTime = bookingRepository.getBookingByPatient(patientName)
                .stream()
                .filter(e -> e.getMeetingTime().equals(time)).toList();
        if (bookingForPatientAtTime.size() > 0) {
            log.error("Already booked at time");
            return false;
        }

        //check if doctor is not free
        if (!slotRepository.bookSlotForDoctor(doctorName, time, patientName)) {
            return false;
        }
        Booking booking = new Booking(doctorName, patientName, time);
        bookingRepository.addBooking(booking);
        return true;
    }

    public synchronized boolean  cancelAppointment(String doctorName, String patientName, LocalTime time) {
        if (doctorRepository.getUser(doctorName) == null) {
            return false;
        }
        List<Booking> bookingsForPatient = bookingRepository.getBookingByPatient(patientName);
        List<Booking> bookings = bookingsForPatient.stream()
                .filter(e -> e.getDoctorName().equals(doctorName) && e.getMeetingTime().equals(time))
                .toList();
        if (bookings.size() != 1) {
            log.error("Why not one? ");
            return false;
        }
        Booking booking = bookings.get(0);
        bookingRepository.removeBooking(booking);
        slotRepository.releaseSlotForDoctor(doctorName, time);
        return true;
    }
}
