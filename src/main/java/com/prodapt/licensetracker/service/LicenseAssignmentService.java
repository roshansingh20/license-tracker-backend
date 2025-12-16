package com.prodapt.licensetracker.service;

import com.prodapt.licensetracker.audit.AuditLog;
import com.prodapt.licensetracker.audit.AuditLogRepository;
import com.prodapt.licensetracker.entity.*;
import com.prodapt.licensetracker.exception.BusinessException;
import com.prodapt.licensetracker.repository.DeviceLicenseRepository;
import com.prodapt.licensetracker.repository.DeviceRepository;
import com.prodapt.licensetracker.repository.LicenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class LicenseAssignmentService {

    private final DeviceRepository deviceRepository;
    private final LicenseRepository licenseRepository;
    private final DeviceLicenseRepository deviceLicenseRepository;
    private final AuditLogRepository auditLogRepository;

    public LicenseAssignmentService(DeviceRepository deviceRepository,
                                    LicenseRepository licenseRepository,
                                    DeviceLicenseRepository deviceLicenseRepository,
                                    AuditLogRepository auditLogRepository) {
        this.deviceRepository = deviceRepository;
        this.licenseRepository = licenseRepository;
        this.deviceLicenseRepository = deviceLicenseRepository;
        this.auditLogRepository = auditLogRepository;
    }

    /**
     * Assign a license to a device with full business validation
     */
    public void assignLicense(String deviceId,
                              String licenseKey,
                              String performedBy) {

        // 1️⃣ Validate device
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() ->
                        new BusinessException("Device not found"));

        if (device.getStatus() == DeviceStatus.DECOMMISSIONED) {
            throw new BusinessException(
                    "Cannot assign license to decommissioned device");
        }

        // 2️⃣ Validate license
        License license = licenseRepository.findById(licenseKey)
                .orElseThrow(() ->
                        new BusinessException("License not found"));

        if (LocalDate.now().isAfter(license.getValidTo())) {
            throw new BusinessException("License is expired");
        }

        if (license.getCurrentUsage() >= license.getMaxUsage()) {
            throw new BusinessException("License usage limit exceeded");
        }

        // 3️⃣ Prevent duplicate assignment
        boolean alreadyAssigned =
                deviceLicenseRepository
                        .existsByDeviceIdAndLicenseKeyAndActiveTrue(
                                deviceId, licenseKey);

        if (alreadyAssigned) {
            throw new BusinessException(
                    "License already assigned to this device");
        }

        // 4️⃣ Create mapping
        DeviceLicense mapping = new DeviceLicense();
        mapping.setDeviceId(deviceId);
        mapping.setLicenseKey(licenseKey);
        mapping.setAssignedAt(LocalDateTime.now());
        mapping.setActive(true);

        deviceLicenseRepository.save(mapping);

        // 5️⃣ Update license usage
        license.setCurrentUsage(
                license.getCurrentUsage() + 1);
        licenseRepository.save(license);

        // 6️⃣ Audit log
        auditLogRepository.save(
                new AuditLog(
                        null,
                        "ASSIGN_LICENSE",
                        performedBy,
                        deviceId + " -> " + licenseKey,
                        LocalDateTime.now(),
                        "SUCCESS"
                )
        );
    }
}
