package com.prodapt.licensetracker.service;

import com.prodapt.licensetracker.audit.AuditLog;
import com.prodapt.licensetracker.audit.AuditLogRepository;
import com.prodapt.licensetracker.dto.LicenseRequest;
import com.prodapt.licensetracker.entity.License;
import com.prodapt.licensetracker.repository.LicenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LicenseService {

    private final LicenseRepository licenseRepository;
    private final AuditLogRepository auditLogRepository;

    public LicenseService(LicenseRepository licenseRepository,
                          AuditLogRepository auditLogRepository) {
        this.licenseRepository = licenseRepository;
        this.auditLogRepository = auditLogRepository;
    }

    public License addLicense(LicenseRequest request, String performedBy) {

        License license = new License();
        license.setLicenseKey(request.getLicenseKey());
        license.setSoftwareName(request.getSoftwareName());
        license.setVendor(request.getVendor());
        license.setValidFrom(request.getValidFrom());
        license.setValidTo(request.getValidTo());
        license.setMaxUsage(request.getMaxUsage());
        license.setCurrentUsage(0);
        license.setLicenseType(request.getLicenseType());

        licenseRepository.save(license);

        audit("ADD_LICENSE", performedBy, license.getLicenseKey());

        return license;
    }

    public List<License> getAllLicenses() {
        return licenseRepository.findAll();
    }

    private void audit(String action, String user, String ref) {
        auditLogRepository.save(new AuditLog(
                null,
                action,
                user,
                ref,
                LocalDateTime.now(),
                "SUCCESS"
        ));
    }
}
