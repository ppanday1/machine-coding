package org.example.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Booking {
    private String bookingId;
    private String centerName;
    private String userName;
    private int startTime;
    private int endTime;
    private String slotId;

    private Activity activity;

    public Booking(String centerName, String userName, int startTime, int endTime, String slotId, Activity activity) {
        this.centerName = centerName;
        this.userName = userName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.slotId = slotId;
        this.activity = activity;
        this.bookingId = UUID.randomUUID().toString();
    }
}
