package com.prodapt.licensetracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "licenses")
public class License {

    @Id
    @Column(name = "license_key")
    private String licenseKey;

    private String softwareName;

    private String vendor;

    private LocalDate validFrom;

    private LocalDate validTo;

    private int maxUsage;

    private int currentUsage;

    @Enumerated(EnumType.STRING)
    private LicenseType licenseType;
}
