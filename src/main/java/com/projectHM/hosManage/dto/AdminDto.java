package com.projectHM.hosManage.dto;

import com.projectHM.hosManage.Entities.Appointment;
import com.projectHM.hosManage.Entities.Doctor;
import com.projectHM.hosManage.Entities.Patient;
import jakarta.transaction.Transactional;

public class AdminDto {
}

//
//@Transactional
//public Appointment createdAppointmentbyId(Appointment appointment, Long doctorId, Long patientId) {
//
//    // 1. Clear Exception messages ke saath fetch karein
//    Doctor doct = doctorRepo.findById(doctorId)
//            .orElseThrow(() -> new IllegalArgumentException("Doctor not found with ID: " + doctorId));
//
//    Patient patient = patientRepo.findById(patientId)
//            .orElseThrow(() -> new IllegalArgumentException("Patient not found with ID: " + patientId));
//
//    // 2. ID check
//    if (appointment.getId() != null) {
//        throw new IllegalArgumentException("New appointment should not already have an ID");
//    }
//
//    // 3. Set relationships
//    appointment.setPatient(patient);
//    appointment.setDoctor(doct);
//    appointment.setStatus("SCHEDULED"); // Set default status if missing
//
//    // 4. Bidirectional List Null-Check Safety
//    if (patient.getAppointments() != null) {
//        patient.getAppointments().add(appointment);
//    }
//
//    return appointmentRepo.save(appointment);
//}
//
//@Transactional
//public Appointment reAssignAppointmmetToOtherDr(Long id , Long Doctorid){
//    Appointment appointment = appointmentRepo.findById(id).orElseThrow();
//    Doctor doc = doctorRepo.findById(Doctorid).orElseThrow();
//
//    appointment.setDoctor(doc);
//    return appointment;
//}
//
//
//
