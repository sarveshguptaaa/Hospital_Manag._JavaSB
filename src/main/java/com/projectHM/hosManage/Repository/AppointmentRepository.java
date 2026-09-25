package com.projectHM.hosManage.Repository;

import com.projectHM.hosManage.Entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByDoctorIdAndAppointmentTime(Long doctorId, LocalDateTime appointmentTime);

    List<Appointment> findByPatientId(Long patientId);

    // Doctor wala bhi saath me rakh lo (Aage kaam aayega)
    List<Appointment> findByDoctorId(Long doctorId);
    // Same Doctor + Same Patient + Same Day Checking Query
    @Query("SELECT COUNT(a) > 0 FROM Appointment a WHERE a.patient.id = :patientId " +
            "AND a.doctor.id = :doctorId " +
            "AND CAST(a.appointmentTime AS date) = CAST(:appointmentTime AS date)")
    boolean existsSameDayBooking(
            @Param("patientId") Long patientId,
            @Param("doctorId") Long doctorId,
            @Param("appointmentTime") LocalDateTime appointmentTime
    );


    boolean existsByPatientIdAndDoctorIdAndAppointmentTimeBetween(
            Long patientId,
            Long doctorId,
            LocalDateTime startOfDay,
            LocalDateTime endOfDay
    );



}