package com.prodapt.licensetracker.repository;

import com.prodapt.licensetracker.entity.DeviceLicense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceLicenseRepository
        extends JpaRepository<DeviceLicense, Long> {

    List<DeviceLicense> findByLicenseKeyAndActiveTrue(String licenseKey);

    boolean existsByDeviceIdAndLicenseKeyAndActiveTrue(
            String deviceId, String licenseKey);
    List<DeviceLicense> findByDeviceIdAndActiveTrue(String deviceId);
}
