package com.prodapt.licensetracker.dto;

import com.prodapt.licensetracker.entity.LicenseType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LicenseRequest {

    private String licenseKey;
    private String softwareName;
    private String vendor;
    private LocalDate validFrom;
    private LocalDate validTo;
    private int maxUsage;
    private LicenseType licenseType;
}
