package com.mediconnect.mediconnect_backend.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String userType; // PATIENT or DOCTOR
    // Patient-specific
    private String address;
    private String gender;
    private String dateOfBirth;
    private String insuranceDetails;
    // Doctor-specific
    private String specialization;
    private String licenseNumber;
    private String qualifications;
}