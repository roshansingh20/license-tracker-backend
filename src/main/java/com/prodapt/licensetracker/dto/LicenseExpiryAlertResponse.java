package com.prodapt.licensetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class LicenseExpiryAlertResponse {

    private String licenseKey;
    private String softwareName;
    private LocalDate expiryDate;
    private long daysRemaining;
    private String status;
}
