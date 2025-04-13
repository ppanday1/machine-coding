package org.example.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;

@Data
public class Sprint {
    private String sprintId;
    private HashSet<String> ticketIds;
    private LocalDate startDate;
    private LocalDate endDate;//should be taken input param but leaving for now since only one sprint

    public Sprint() {
        ticketIds = new HashSet<>();
    }
}
