package com.prodapt.licensetracker.controller;

import com.prodapt.licensetracker.dto.LicenseExpiryAlertResponse;
import com.prodapt.licensetracker.service.LicenseAlertService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class LicenseAlertController {

    private final LicenseAlertService alertService;

    public LicenseAlertController(LicenseAlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping("/expiring")
    public List<LicenseExpiryAlertResponse> getExpiringLicenses() {
        return alertService.getExpiringLicenses();
    }
}
