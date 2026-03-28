package com.mediconnect.mediconnect_backend.controller;


import com.mediconnect.mediconnect_backend.dto.HealthRecordDTO;
import com.mediconnect.mediconnect_backend.service.HealthRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/health-records")
@RequiredArgsConstructor
public class HealthRecordController {

    private final HealthRecordService healthRecordService;

    @GetMapping
    public ResponseEntity<List<HealthRecordDTO>> getAllRecords() {
        return ResponseEntity.ok(healthRecordService.getAllRecords());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HealthRecordDTO> getRecordById(@PathVariable Long id) {
        return ResponseEntity.ok(healthRecordService.getRecordById(id));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<HealthRecordDTO>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(healthRecordService.getRecordsByPatient(patientId));
    }

    @PostMapping
    public ResponseEntity<HealthRecordDTO> createRecord(@RequestBody HealthRecordDTO dto) {
        return ResponseEntity.ok(healthRecordService.createRecord(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HealthRecordDTO> updateRecord(@PathVariable Long id, @RequestBody HealthRecordDTO dto) {
        return ResponseEntity.ok(healthRecordService.updateRecord(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        healthRecordService.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }
}
