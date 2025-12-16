package com.prodapt.licensetracker.controller;

import com.prodapt.licensetracker.service.LicenseAssignmentService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assignments")
public class LicenseAssignmentController {

    private final LicenseAssignmentService service;

    public LicenseAssignmentController(LicenseAssignmentService service) {
        this.service = service;
    }

    @PostMapping
    public String assign(@RequestParam String deviceId,
                         @RequestParam String licenseKey,
                         Authentication authentication) {

        String user =
                (authentication != null) ? authentication.getName() : "SYSTEM";

        service.assignLicense(deviceId, licenseKey, user);

        return "License assigned successfully";
    }
}
