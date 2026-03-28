package com.mediconnect.mediconnect_backend.repository;


import com.mediconnect.mediconnect_backend.entity.HealthReport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HealthReportRepository extends JpaRepository<HealthReport, Long> {
    List<HealthReport> findByReportType(String reportType);
}