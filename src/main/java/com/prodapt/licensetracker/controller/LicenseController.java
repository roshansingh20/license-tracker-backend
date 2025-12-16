package com.prodapt.licensetracker.controller;

import com.prodapt.licensetracker.dto.LicenseRequest;
import com.prodapt.licensetracker.entity.License;
import com.prodapt.licensetracker.service.LicenseService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/licenses")
public class LicenseController {

    private final LicenseService licenseService;

    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @PostMapping
    public License addLicense(@RequestBody LicenseRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String performedBy =
                (authentication != null && authentication.isAuthenticated())
                        ? authentication.getName()
                        : "SYSTEM";

        return licenseService.addLicense(request, performedBy);
    }

    @GetMapping
    public List<License> getAllLicenses() {
        return licenseService.getAllLicenses();
    }
}
