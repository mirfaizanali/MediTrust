package com.mediconnect.mediconnect_backend.service;


import com.mediconnect.mediconnect_backend.dto.PatientDTO;
import com.mediconnect.mediconnect_backend.entity.Patient;
import com.mediconnect.mediconnect_backend.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PatientDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
        return toDTO(patient);
    }

    public PatientDTO updatePatient(Long id, PatientDTO dto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));

        patient.setName(dto.getName());
        patient.setPhone(dto.getPhone());
        patient.setAddress(dto.getAddress());
        patient.setGender(dto.getGender());
        patient.setDateOfBirth(dto.getDateOfBirth());
        patient.setInsuranceDetails(dto.getInsuranceDetails());
        if (dto.getHealthStatus() != null) {
            patient.setHealthStatus(Patient.HealthStatus.valueOf(dto.getHealthStatus()));
        }

        Patient saved = patientRepository.save(patient);
        return toDTO(saved);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public PatientDTO toDTO(Patient p) {
        return PatientDTO.builder()
                .patientId(p.getPatientId())
                .name(p.getName())
                .email(p.getEmail())
                .phone(p.getPhone())
                .address(p.getAddress())
                .gender(p.getGender())
                .dateOfBirth(p.getDateOfBirth())
                .insuranceDetails(p.getInsuranceDetails())
                .healthStatus(p.getHealthStatus().name())
                .role(p.getRole().name())
                .build();
    }
}