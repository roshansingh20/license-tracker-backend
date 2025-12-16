package com.prodapt.licensetracker.repository;

import com.prodapt.licensetracker.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, String> {
}
