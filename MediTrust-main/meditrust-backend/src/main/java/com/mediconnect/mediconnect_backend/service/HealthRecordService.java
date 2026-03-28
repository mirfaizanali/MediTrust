package com.mediconnect.mediconnect_backend.service;

import com.mediconnect.mediconnect_backend.dto.HealthRecordDTO;
import com.mediconnect.mediconnect_backend.entity.*;
import com.mediconnect.mediconnect_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HealthRecordService {

    private final HealthRecordRepository healthRecordRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    private static final DateTimeFormatter DTF = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public List<HealthRecordDTO> getAllRecords() {
        return healthRecordRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public HealthRecordDTO getRecordById(Long id) {
        HealthRecord r = healthRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Health record not found with id: " + id));
        return toDTO(r);
    }

    public List<HealthRecordDTO> getRecordsByPatient(Long patientId) {
        return healthRecordRepository.findByPatientPatientId(patientId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public HealthRecordDTO createRecord(HealthRecordDTO dto) {
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Doctor doctor = null;
        if (dto.getDoctorId() != null) {
            doctor = doctorRepository.findById(dto.getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));
        }

        HealthRecord r = HealthRecord.builder()
                .patient(patient)
                .doctor(doctor)
                .diagnosis(dto.getDiagnosis())
                .labResults(dto.getLabResults())
                .treatmentPlan(dto.getTreatmentPlan())
                .notes(dto.getNotes())
                .build();

        HealthRecord saved = healthRecordRepository.save(r);
        return toDTO(saved);
    }

    public HealthRecordDTO updateRecord(Long id, HealthRecordDTO dto) {
        HealthRecord r = healthRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Health record not found"));

        r.setDiagnosis(dto.getDiagnosis());
        r.setLabResults(dto.getLabResults());
        r.setTreatmentPlan(dto.getTreatmentPlan());
        r.setNotes(dto.getNotes());

        HealthRecord saved = healthRecordRepository.save(r);
        return toDTO(saved);
    }

    public void deleteRecord(Long id) {
        healthRecordRepository.deleteById(id);
    }

    private HealthRecordDTO toDTO(HealthRecord r) {
        return HealthRecordDTO.builder()
                .recordId(r.getRecordId())
                .patientId(r.getPatient().getPatientId())
                .patientName(r.getPatient().getName())
                .doctorId(r.getDoctor() != null ? r.getDoctor().getDoctorId() : null)
                .doctorName(r.getDoctor() != null ? r.getDoctor().getName() : null)
                .diagnosis(r.getDiagnosis())
                .labResults(r.getLabResults())
                .treatmentPlan(r.getTreatmentPlan())
                .notes(r.getNotes())
                .updatedDate(r.getUpdatedDate() != null ? r.getUpdatedDate().format(DTF) : null)
                .build();
    }
}