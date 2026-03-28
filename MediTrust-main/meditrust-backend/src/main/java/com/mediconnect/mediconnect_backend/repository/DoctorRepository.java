package com.mediconnect.mediconnect_backend.repository;


import com.mediconnect.mediconnect_backend.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Doctor> findBySpecialization(String specialization);
    List<Doctor> findByAvailableTrue();
}
