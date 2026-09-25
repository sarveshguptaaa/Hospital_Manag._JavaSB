package com.projectHM.hosManage.dto;

import com.projectHM.hosManage.Entities.Appointment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Patientdto {

    private Long id;

    private String name;

    private String email;

    private LocalDate birthdate;

    private String gender;

    private String bloodGroup;

    private String bloodGroupType;

    private LocalDateTime createdAt;

    // Agar Patient ke saath Insurance details bhi bhejni hain
    private Insurancedto insurance;

    // Agar appointments bhi response me chahiye to

     private List<Appointment> appointments;
}