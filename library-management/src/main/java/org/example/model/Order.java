package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Order {
    private String userId;
    private String bookName;
    private LocalDate returnDate;
}
