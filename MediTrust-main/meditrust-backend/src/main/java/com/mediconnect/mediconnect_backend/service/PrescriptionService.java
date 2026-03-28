package com.mediconnect.mediconnect_backend.service;



import com.mediconnect.mediconnect_backend.dto.PrescriptionDTO;
import com.mediconnect.mediconnect_backend.entity.*;
import com.mediconnect.mediconnect_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    private static final DateTimeFormatter DTF = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public List<PrescriptionDTO> getAllPrescriptions() {
        return prescriptionRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PrescriptionDTO getPrescriptionById(Long id) {
        Prescription p = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + id));
        return toDTO(p);
    }

    public List<PrescriptionDTO> getPrescriptionsByPatient(Long patientId) {
        return prescriptionRepository.findByPatientPatientId(patientId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PrescriptionDTO> getPrescriptionsByDoctor(Long doctorId) {
        return prescriptionRepository.findByDoctorDoctorId(doctorId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PrescriptionDTO createPrescription(PrescriptionDTO dto) {
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Prescription p = Prescription.builder()
                .patient(patient)
                .doctor(doctor)
                .medicationList(dto.getMedicationList())
                .dosage(dto.getDosage())
                .instructions(dto.getInstructions())
                .status(Prescription.PrescriptionStatus.ACTIVE)
                .expiryDate(dto.getExpiryDate() != null ? LocalDateTime.parse(dto.getExpiryDate(), DTF) : null)
                .build();

        Prescription saved = prescriptionRepository.save(p);
        return toDTO(saved);
    }

    public PrescriptionDTO updatePrescriptionStatus(Long id, String status) {
        Prescription p = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));
        p.setStatus(Prescription.PrescriptionStatus.valueOf(status));
        Prescription saved = prescriptionRepository.save(p);
        return toDTO(saved);
    }

    public void deletePrescription(Long id) {
        prescriptionRepository.deleteById(id);
    }

    private PrescriptionDTO toDTO(Prescription p) {
        return PrescriptionDTO.builder()
                .prescriptionId(p.getPrescriptionId())
                .patientId(p.getPatient().getPatientId())
                .patientName(p.getPatient().getName())
                .doctorId(p.getDoctor().getDoctorId())
                .doctorName(p.getDoctor().getName())
                .medicationList(p.getMedicationList())
                .dosage(p.getDosage())
                .instructions(p.getInstructions())
                .status(p.getStatus().name())
                .prescribedDate(p.getPrescribedDate() != null ? p.getPrescribedDate().format(DTF) : null)
                .expiryDate(p.getExpiryDate() != null ? p.getExpiryDate().format(DTF) : null)
                .build();
    }
}