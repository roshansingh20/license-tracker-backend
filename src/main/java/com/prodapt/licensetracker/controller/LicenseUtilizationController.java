package com.prodapt.licensetracker.controller;

import com.prodapt.licensetracker.dto.LicenseUtilizationResponse;
import com.prodapt.licensetracker.service.LicenseUtilizationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/utilization")
public class LicenseUtilizationController {

    private final LicenseUtilizationService service;

    public LicenseUtilizationController(LicenseUtilizationService service) {
        this.service = service;
    }

    @GetMapping("/{licenseKey}")
    public LicenseUtilizationResponse getUsage(
            @PathVariable String licenseKey) {

        return service.getUtilization(licenseKey);
    }
}
