package com.mediconnect.mediconnect_backend.dto;



import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDTO {
    private Long patientId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private String dateOfBirth;
    private String insuranceDetails;
    private String healthStatus;
    private String role;
}