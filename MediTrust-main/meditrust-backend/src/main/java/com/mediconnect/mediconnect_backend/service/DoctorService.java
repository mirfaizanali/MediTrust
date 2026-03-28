package com.mediconnect.mediconnect_backend.service;


import com.mediconnect.mediconnect_backend.dto.DoctorDTO;
import com.mediconnect.mediconnect_backend.entity.Doctor;
import com.mediconnect.mediconnect_backend.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public DoctorDTO getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
        return toDTO(doctor);
    }

    public List<DoctorDTO> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<DoctorDTO> getAvailableDoctors() {
        return doctorRepository.findByAvailableTrue().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public DoctorDTO updateDoctor(Long id, DoctorDTO dto) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));

        doctor.setName(dto.getName());
        doctor.setPhone(dto.getPhone());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setQualifications(dto.getQualifications());
        doctor.setAvailable(dto.isAvailable());

        Doctor saved = doctorRepository.save(doctor);
        return toDTO(saved);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    public DoctorDTO toDTO(Doctor d) {
        return DoctorDTO.builder()
                .doctorId(d.getDoctorId())
                .name(d.getName())
                .email(d.getEmail())
                .phone(d.getPhone())
                .specialization(d.getSpecialization())
                .licenseNumber(d.getLicenseNumber())
                .qualifications(d.getQualifications())
                .available(d.isAvailable())
                .build();
    }
}