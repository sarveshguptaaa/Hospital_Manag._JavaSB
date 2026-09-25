package com.projectHM.hosManage.service;


import com.projectHM.hosManage.Entities.Doctor;
import com.projectHM.hosManage.Repository.DoctorRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiice {

    private final DoctorRepo doctorRepo;

    public DoctorServiice(DoctorRepo doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    public List<Doctor>findAll(){

        List<Doctor>docs = doctorRepo.findAll();

        return docs;
    }


    public Optional<Doctor> findById(Long id) {

        return doctorRepo.findById(id);
    }


    public Optional<Doctor> findByEmail(String email) {
        return doctorRepo.findByEmail(email);
    }

    public Doctor save(Doctor doctor){
        return doctorRepo.save(doctor);
    }

    public void deleteById(Long id){
        doctorRepo.deleteById(id);
    }


    @Transactional
    public Doctor updateDoctor(Long id, Doctor updatedData) {
        Doctor existingDoctor = doctorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + id));

        // Check if new email belongs to another doctor
        if (updatedData.getEmail() != null && !updatedData.getEmail().equalsIgnoreCase(existingDoctor.getEmail())) {
            if (doctorRepo.existsByEmail(updatedData.getEmail())) {
                throw new IllegalArgumentException("Email '" + updatedData.getEmail() + "' is already in use by another doctor.");
            }
            existingDoctor.setEmail(updatedData.getEmail());
        }

        if (updatedData.getName() != null) {
            existingDoctor.setName(updatedData.getName());
        }

        if (updatedData.getSpecialization() != null) {
            existingDoctor.setSpecialization(updatedData.getSpecialization());
        }

        return doctorRepo.save(existingDoctor);
    }
}
