package com.projectHM.hosManage.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequestdto {



        private Long patientId;
        private Long doctorId;

         @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
         private LocalDateTime appointmentTime;
        private String reason;
    }

