package com.mediconnect.mediconnect_backend.repository;


import com.mediconnect.mediconnect_backend.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByPatientPatientId(Long patientId);
    List<Prescription> findByDoctorDoctorId(Long doctorId);
    List<Prescription> findByStatus(Prescription.PrescriptionStatus status);
    long countByStatus(Prescription.PrescriptionStatus status);
}
