package com.prodapt.licensetracker.repository;

import com.prodapt.licensetracker.entity.License;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseRepository extends JpaRepository<License, String> {
}
