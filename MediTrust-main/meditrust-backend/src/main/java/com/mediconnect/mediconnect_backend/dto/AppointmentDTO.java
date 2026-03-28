package com.mediconnect.mediconnect_backend.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDTO {
    private Long appointmentId;
    private Long patientId;
    private String patientName;
    private Long doctorId;
    private String doctorName;
    private String dateTime;
    private String mode;
    private String status;
    private String notes;
    private String meetingLink;
}
