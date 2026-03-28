package com.mediconnect.mediconnect_backend.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardStats {
    private long totalPatients;
    private long totalDoctors;
    private long totalAppointments;
    private long todayAppointments;
    private long activePrescriptions;
    private long totalRecords;
}