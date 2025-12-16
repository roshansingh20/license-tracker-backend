package com.prodapt.licensetracker.service;

import com.prodapt.licensetracker.dto.NonCompliantDeviceResponse;
import com.prodapt.licensetracker.entity.*;
import com.prodapt.licensetracker.repository.DeviceLicenseRepository;
import com.prodapt.licensetracker.repository.DeviceRepository;
import com.prodapt.licensetracker.repository.LicenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ComplianceReportService {

    private final DeviceRepository deviceRepository;
    private final DeviceLicenseRepository deviceLicenseRepository;
    private final LicenseRepository licenseRepository;

    public ComplianceReportService(DeviceRepository deviceRepository,
                                   DeviceLicenseRepository deviceLicenseRepository,
                                   LicenseRepository licenseRepository) {
        this.deviceRepository = deviceRepository;
        this.deviceLicenseRepository = deviceLicenseRepository;
        this.licenseRepository = licenseRepository;
    }

    public List<NonCompliantDeviceResponse> getNonCompliantDevices() {

        List<NonCompliantDeviceResponse> report = new ArrayList<>();

        List<Device> devices = deviceRepository.findAll();

        for (Device device : devices) {

            if (device.getStatus() == DeviceStatus.DECOMMISSIONED) {
                continue;
            }

            List<DeviceLicense> licenses =
                    deviceLicenseRepository.findByDeviceIdAndActiveTrue(
                            device.getDeviceId());


            if (licenses.isEmpty()) {
                report.add(new NonCompliantDeviceResponse(
                        device.getDeviceId(),
                        device.getType().name(),
                        device.getLocation(),
                        "NO LICENSE ASSIGNED"
                ));
                continue;
            }


            for (DeviceLicense dl : licenses) {
                License license =
                        licenseRepository.findById(dl.getLicenseKey()).orElse(null);

                if (license != null &&
                        LocalDate.now().isAfter(license.getValidTo())) {

                    report.add(new NonCompliantDeviceResponse(
                            device.getDeviceId(),
                            device.getType().name(),
                            device.getLocation(),
                            "LICENSE EXPIRED (" + license.getLicenseKey() + ")"
                    ));
                }
                System.out.println("Checking device: " + device.getDeviceId());
                System.out.println("Licenses found: " + licenses.size());

            }
        }

        return report;

    }

}
