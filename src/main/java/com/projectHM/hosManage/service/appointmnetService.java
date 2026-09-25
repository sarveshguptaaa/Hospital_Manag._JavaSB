package com.projectHM.hosManage.service;

import com.projectHM.hosManage.Entities.Appointment;
import com.projectHM.hosManage.Entities.Doctor;
import com.projectHM.hosManage.Entities.Patient;
import com.projectHM.hosManage.Repository.AppointmentRepository;
import com.projectHM.hosManage.Repository.DoctorRepo;
import com.projectHM.hosManage.Repository.Patient_Repo;
import com.projectHM.hosManage.dto.AppointmentRequestdto;
import com.projectHM.hosManage.dto.AppointmentResponsedto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class appointmnetService {

    private final Patient_Repo patientRepo;
    private final DoctorRepo doctorRepo;
    private final AppointmentRepository appointmentRepo;

    @Transactional
    public AppointmentResponsedto bookAppointment(AppointmentRequestdto request) {

        // 1. RULE 1: Doctor ka same exact Time Slot already Booked to nahi hai?
        boolean isSlotTaken = appointmentRepo.existsByDoctorIdAndAppointmentTime(
                request.getDoctorId(),
                request.getAppointmentTime()
        );
        if (isSlotTaken) {
            throw new IllegalArgumentException("Doctor is already booked for this specific time slot!");
        }

        // 2. RULE 2: Same Day Duplicate Check (Bytea Error Fix)
        LocalDateTime appointmentTime = request.getAppointmentTime();
        LocalDateTime startOfDay = appointmentTime.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = appointmentTime.toLocalDate().atTime(LocalTime.MAX);

        boolean isSameDayBooked = appointmentRepo.existsByPatientIdAndDoctorIdAndAppointmentTimeBetween(
                request.getPatientId(),
                request.getDoctorId(),
                startOfDay,
                endOfDay
        );

        if (isSameDayBooked) {
            throw new IllegalArgumentException("Patient already has an appointment with this doctor on the same date!");
        }

        // 3. Fetch Patient & Doctor
        Patient patient = patientRepo.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + request.getPatientId()));

        Doctor doctor = doctorRepo.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + request.getDoctorId()));

        // 4. Create & Save Appointment
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setReason(request.getReason());
        appointment.setStatus("SCHEDULED");

        Appointment savedAppointment = appointmentRepo.save(appointment);
        return convertToResponseDto(savedAppointment);
    }

    @Transactional
    public Appointment reAssignAppointmentToOtherDr(Long id, Long doctorId) {
        Appointment appointment = appointmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + id));
        Doctor doc = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + doctorId));

        appointment.setDoctor(doc);
        return appointmentRepo.save(appointment);
    }

    public List<AppointmentResponsedto> getAppointmentsByPatient(Long patientId) {
        return appointmentRepo.findByPatientId(patientId).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentResponsedto> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepo.findByDoctorId(doctorId).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<AppointmentResponsedto> updateStatus(Long id, String status) {
        Optional<Appointment> opt = appointmentRepo.findById(id);
        if (opt.isEmpty()) {
            return Optional.empty();
        }

        Appointment appointment = opt.get();
        appointment.setStatus(status.toUpperCase());
        Appointment updated = appointmentRepo.save(appointment);

        return Optional.of(convertToResponseDto(updated));
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (appointmentRepo.existsById(id)) {
            appointmentRepo.deleteById(id);
            return true;
        }
        return false;
    }

    // SINGLE HELPER METHOD (Clean & Safe Option 1 Mapping)
    private AppointmentResponsedto convertToResponseDto(Appointment appointment) {
        AppointmentResponsedto dto = new AppointmentResponsedto();

        dto.setId(appointment.getId());
        dto.setAppointmentTime(appointment.getAppointmentTime());
        dto.setStatus(appointment.getStatus());
        dto.setReason(appointment.getReason());

        if (appointment.getPatient() != null) {
            dto.setPatientId(appointment.getPatient().getId());
        }

        if (appointment.getDoctor() != null) {
            dto.setDoctorId(appointment.getDoctor().getId());
        }

        return dto;
    }
}