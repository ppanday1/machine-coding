package org.example.repository;

import org.example.model.Doctor;
import org.example.model.Speciality;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class DoctorRepository {
    HashMap<String, Doctor> doctors;

    public DoctorRepository() {
        doctors = new HashMap<>();
    }

    public boolean addDoctor(String name, Speciality speciality) {
        if (doctors.containsKey(name)) {
            return false;
        }
        doctors.put(name, new Doctor(name, speciality));
        return true;
    }

    public List<Doctor> findDoctorForSpeciality(Speciality speciality) {
        List<Doctor> result = new ArrayList<>();
        for (Doctor doctor : doctors.values()) {
            if (doctor.getSpeciality().equals(speciality)) {
                result.add(doctor);
            }
        }
        return result;
    }

    public Doctor getDoctor(String name) {
        return doctors.get(name);
    }
}
