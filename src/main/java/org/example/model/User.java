package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {
    private String emailId;
    private String name;
    private LocalDate membershipStartDate;
    private LocalDate membershipEndDate;
}
