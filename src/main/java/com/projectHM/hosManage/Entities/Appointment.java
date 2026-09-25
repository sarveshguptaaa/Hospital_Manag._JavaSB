package com.projectHM.hosManage.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "patient")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "appointment_time")
    private LocalDateTime appointmentTime;

    @Column(length = 500)
    private String reason;

    private String status ;

    //many to one sideme cascading na hoi kro define kuiki ek delete hia baki bhi dlete hinge
    //many appointment toOne patient
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name ="patient_id" , nullable = false)
//    private Patient patient;
//
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "doctor_id", nullable = false)
//    private Doctor doctor;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonBackReference // Child side par BackReference
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    @JsonBackReference // Doctor ke liye bhi same
    private Doctor doctor;

}
