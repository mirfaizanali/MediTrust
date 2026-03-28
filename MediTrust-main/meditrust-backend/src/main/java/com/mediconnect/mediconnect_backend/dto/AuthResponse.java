package com.mediconnect.mediconnect_backend.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    private String userType;
    private Long userId;
    private String name;
    private String email;
}