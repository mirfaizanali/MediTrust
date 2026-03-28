package com.mediconnect.mediconnect_backend.dto;



import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorDTO {
    private Long doctorId;
    private String name;
    private String email;
    private String phone;
    private String specialization;
    private String licenseNumber;
    private String qualifications;
    private boolean available;
}