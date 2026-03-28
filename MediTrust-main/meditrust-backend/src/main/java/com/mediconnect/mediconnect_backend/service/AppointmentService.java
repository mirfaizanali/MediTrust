package com.mediconnect.mediconnect_backend.service;


import com.mediconnect.mediconnect_backend.dto.AppointmentDTO;
import com.mediconnect.mediconnect_backend.entity.*;
import com.mediconnect.mediconnect_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    private static final DateTimeFormatter DTF = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AppointmentDTO getAppointmentById(Long id) {
        Appointment a = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
        return toDTO(a);
    }

    public List<AppointmentDTO> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientPatientId(patientId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<AppointmentDTO> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorDoctorId(doctorId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AppointmentDTO createAppointment(AppointmentDTO dto) {
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Appointment a = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .dateTime(LocalDateTime.parse(dto.getDateTime(), DTF))
                .mode(Appointment.AppointmentMode.valueOf(dto.getMode()))
                .status(Appointment.AppointmentStatus.SCHEDULED)
                .notes(dto.getNotes())
                .build();

        if (a.getMode() == Appointment.AppointmentMode.VIRTUAL) {
            a.setMeetingLink("https://meet.mediconnect.com/" + UUID.randomUUID().toString().substring(0, 8));
        }

        Appointment saved = appointmentRepository.save(a);
        return toDTO(saved);
    }

    public AppointmentDTO updateAppointmentStatus(Long id, String status) {
        Appointment a = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        a.setStatus(Appointment.AppointmentStatus.valueOf(status));
        Appointment saved = appointmentRepository.save(a);
        return toDTO(saved);
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    private AppointmentDTO toDTO(Appointment a) {
        return AppointmentDTO.builder()
                .appointmentId(a.getAppointmentId())
                .patientId(a.getPatient().getPatientId())
                .patientName(a.getPatient().getName())
                .doctorId(a.getDoctor().getDoctorId())
                .doctorName(a.getDoctor().getName())
                .dateTime(a.getDateTime().format(DTF))
                .mode(a.getMode().name())
                .status(a.getStatus().name())
                .notes(a.getNotes())
                .meetingLink(a.getMeetingLink())
                .build();
    }
}