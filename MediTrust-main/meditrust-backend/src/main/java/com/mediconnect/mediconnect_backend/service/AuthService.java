package com.mediconnect.mediconnect_backend.service;


import com.mediconnect.mediconnect_backend.dto.*;
import com.mediconnect.mediconnect_backend.entity.*;
import com.mediconnect.mediconnect_backend.repository.*;
import com.mediconnect.mediconnect_backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {
        if ("DOCTOR".equalsIgnoreCase(request.getUserType())) {
            if (doctorRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email already registered");
            }
            Doctor doctor = Doctor.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .phone(request.getPhone())
                    .specialization(request.getSpecialization())
                    .licenseNumber(request.getLicenseNumber())
                    .qualifications(request.getQualifications())
                    .available(true)
                    .role(Patient.Role.DOCTOR)
                    .build();
            Doctor saved = doctorRepository.save(doctor);
            String token = jwtUtil.generateToken(saved.getEmail(), "DOCTOR", saved.getDoctorId());
            return AuthResponse.builder()
                    .token(token)
                    .userType("DOCTOR")
                    .userId(saved.getDoctorId())
                    .name(saved.getName())
                    .email(saved.getEmail())
                    .build();
        } else {
            if (patientRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email already registered");
            }
            Patient patient = Patient.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .phone(request.getPhone())
                    .address(request.getAddress())
                    .gender(request.getGender())
                    .dateOfBirth(request.getDateOfBirth())
                    .insuranceDetails(request.getInsuranceDetails())
                    .healthStatus(Patient.HealthStatus.STABLE)
                    .role(Patient.Role.PATIENT)
                    .build();
            Patient saved = patientRepository.save(patient);
            String token = jwtUtil.generateToken(saved.getEmail(), "PATIENT", saved.getPatientId());
            return AuthResponse.builder()
                    .token(token)
                    .userType("PATIENT")
                    .userId(saved.getPatientId())
                    .name(saved.getName())
                    .email(saved.getEmail())
                    .build();
        }
    }

    public AuthResponse login(AuthRequest request) {
        if ("DOCTOR".equalsIgnoreCase(request.getUserType())) {
            Doctor doctor = doctorRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Invalid credentials"));
            if (!passwordEncoder.matches(request.getPassword(), doctor.getPassword())) {
                throw new RuntimeException("Invalid credentials");
            }
            String token = jwtUtil.generateToken(doctor.getEmail(), "DOCTOR", doctor.getDoctorId());
            return AuthResponse.builder()
                    .token(token)
                    .userType("DOCTOR")
                    .userId(doctor.getDoctorId())
                    .name(doctor.getName())
                    .email(doctor.getEmail())
                    .build();
        } else {
            Patient patient = patientRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Invalid credentials"));
            if (!passwordEncoder.matches(request.getPassword(), patient.getPassword())) {
                throw new RuntimeException("Invalid credentials");
            }
            String token = jwtUtil.generateToken(patient.getEmail(), "PATIENT", patient.getPatientId());
            return AuthResponse.builder()
                    .token(token)
                    .userType("PATIENT")
                    .userId(patient.getPatientId())
                    .name(patient.getName())
                    .email(patient.getEmail())
                    .build();
        }
    }
}