package com.prodapt.licensetracker.dto;

import com.prodapt.licensetracker.entity.DeviceStatus;
import com.prodapt.licensetracker.entity.DeviceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceRequest {

    private String deviceId;
    private String ipAddress;
    private DeviceType type;
    private String location;
    private String model;
    private String vendor;
    private DeviceStatus status;
}
