package com.mediconnect.mediconnect_backend.service;



import com.mediconnect.mediconnect_backend.dto.DashboardStats;
import com.mediconnect.mediconnect_backend.entity.Prescription;
import com.mediconnect.mediconnect_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final HealthRecordRepository healthRecordRepository;

    public DashboardStats getDashboardStats() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        return DashboardStats.builder()
                .totalPatients(patientRepository.count())
                .totalDoctors(doctorRepository.count())
                .totalAppointments(appointmentRepository.count())
                .todayAppointments(appointmentRepository.countTodayAppointments(startOfDay, endOfDay))
                .activePrescriptions(prescriptionRepository.countByStatus(Prescription.PrescriptionStatus.ACTIVE))
                .totalRecords(healthRecordRepository.count())
                .build();
    }
}