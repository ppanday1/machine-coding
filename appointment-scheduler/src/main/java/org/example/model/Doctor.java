package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Doctor {
    private String name;
    private Speciality speciality;
}
