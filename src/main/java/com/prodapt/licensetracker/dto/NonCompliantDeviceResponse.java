package com.prodapt.licensetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NonCompliantDeviceResponse {

    private String deviceId;
    private String deviceType;
    private String location;
    private String issue;
}
