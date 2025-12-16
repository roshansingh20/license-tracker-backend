package com.prodapt.licensetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LicenseUtilizationResponse {

    private String licenseKey;
    private String softwareName;
    private int used;
    private int total;
    private double percentage;
    private String status;
}
