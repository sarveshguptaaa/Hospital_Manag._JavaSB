package com.projectHM.hosManage.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Insurancedto {

    @NotBlank(message = "Policy number is required")
    private String policyNumber;

    @NotBlank(message = "Provider name is required")
    private String provider;

    // FIX: Pattern ko "yyyy-MM-dd" kiya hai taaki Postman me "2026-09-17" bhej sako
    @NotNull(message = "Valid until date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate validUntill;

    @NotNull(message = "Payment amount is required")
    @Positive(message = "Payment must be greater than zero")
    private Long payment;

    // Patient Entity ko map karne ke liye patientId
//    private Long patientId;
}
