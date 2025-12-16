package com.prodapt.licensetracker.controller;

import com.prodapt.licensetracker.dto.NonCompliantDeviceResponse;
import com.prodapt.licensetracker.service.ComplianceReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ComplianceReportController {

    private final ComplianceReportService service;

    public ComplianceReportController(ComplianceReportService service) {
        this.service = service;
    }

    @GetMapping("/non-compliant")
    public List<NonCompliantDeviceResponse> getNonCompliantDevices() {
        return service.getNonCompliantDevices();
    }
}
