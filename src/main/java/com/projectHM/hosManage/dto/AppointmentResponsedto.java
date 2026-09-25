package com.projectHM.hosManage.dto;

import lombok.*;

import java.time.LocalDateTime;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentResponsedto {

    // Example Response
    private Long id;
    private LocalDateTime appointmentTime;
    private String status; // SCHEDULED, CANCELLED, COMPLETED
    private String reason;
    private Long patientId;
    private Long doctorId;

}
