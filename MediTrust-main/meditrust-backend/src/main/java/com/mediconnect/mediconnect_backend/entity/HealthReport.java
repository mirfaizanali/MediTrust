package com.mediconnect.mediconnect_backend.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "health_reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    private String reportType;

    private Double recoveryRate;

    private Double readmissionRate;

    private Integer totalPatients;

    private Integer totalAppointments;

    private Integer totalPrescriptions;

    @Column(columnDefinition = "TEXT")
    private String metricsJson;

    private LocalDateTime generatedDate;

    @PrePersist
    protected void onCreate() {
        generatedDate = LocalDateTime.now();
    }
}