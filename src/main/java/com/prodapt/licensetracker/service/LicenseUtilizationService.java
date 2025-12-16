package com.prodapt.licensetracker.service;

import com.prodapt.licensetracker.dto.LicenseUtilizationResponse;
import com.prodapt.licensetracker.entity.License;
import com.prodapt.licensetracker.repository.LicenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LicenseUtilizationService {

    private final LicenseRepository licenseRepository;

    public LicenseUtilizationService(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    public LicenseUtilizationResponse getUtilization(String licenseKey) {

        License license = licenseRepository.findById(licenseKey)
                .orElseThrow(() -> new RuntimeException("License not found"));

        double percentage =
                ((double) license.getCurrentUsage() / license.getMaxUsage()) * 100;

        String status;

        if (LocalDate.now().isAfter(license.getValidTo())) {
            status = "RED (EXPIRED)";
        } else if (percentage >= 95) {
            status = "RED";
        } else if (percentage >= 80) {
            status = "YELLOW";
        } else {
            status = "GREEN";
        }

        return new LicenseUtilizationResponse(
                license.getLicenseKey(),
                license.getSoftwareName(),
                license.getCurrentUsage(),
                license.getMaxUsage(),
                Math.round(percentage * 100.0) / 100.0,
                status
        );
    }
}
