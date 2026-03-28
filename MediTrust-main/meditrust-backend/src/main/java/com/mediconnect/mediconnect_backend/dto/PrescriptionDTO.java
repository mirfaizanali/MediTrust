package com.mediconnect.mediconnect_backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionDTO {
    private Long prescriptionId;
    private Long patientId;
    private String patientName;
    private Long doctorId;
    private String doctorName;
    private String medicationList;
    private String dosage;
    private String instructions;
    private String status;
    private String prescribedDate;
    private String expiryDate;
}