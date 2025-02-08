package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class Booking {
    private String doctorName;
    private String patientName;
    LocalTime meetingTime;
}
