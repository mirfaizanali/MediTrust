package com.mediconnect.mediconnect_backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthRecordDTO {
    private Long recordId;
    private Long patientId;
    private String patientName;
    private Long doctorId;
    private String doctorName;
    private String diagnosis;
    private String labResults;
    private String treatmentPlan;
    private String notes;
    private String updatedDate;
}