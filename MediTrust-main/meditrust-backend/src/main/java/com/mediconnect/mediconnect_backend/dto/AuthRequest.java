package com.mediconnect.mediconnect_backend.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    private String email;
    private String password;
    private String userType; // PATIENT or DOCTOR
}
