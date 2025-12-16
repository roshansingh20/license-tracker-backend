package com.prodapt.licensetracker.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "devices")
public class Device {

    @Id
    @Column(name = "device_id")
    private String deviceId;

    private String ipAddress;

    @Enumerated(EnumType.STRING)
    private DeviceType type;

    private String location;

    private String model;

    private String vendor;

    @Enumerated(EnumType.STRING)
    private DeviceStatus status;
}
