package com.prodapt.licensetracker.service;

import com.prodapt.licensetracker.dto.LicenseExpiryAlertResponse;
import com.prodapt.licensetracker.entity.License;
import com.prodapt.licensetracker.repository.LicenseRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class LicenseAlertService {

    private final LicenseRepository licenseRepository;

    public LicenseAlertService(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    // Runs every day at 9 AM
    @Scheduled(cron = "0 0 9 * * ?")
    public void runDailyExpiryCheck() {
        getExpiringLicenses();
    }

    public List<LicenseExpiryAlertResponse> getExpiringLicenses() {

        List<LicenseExpiryAlertResponse> alerts = new ArrayList<>();
        List<License> licenses = licenseRepository.findAll();

        LocalDate today = LocalDate.now();

        for (License license : licenses) {

            long daysLeft = ChronoUnit.DAYS.between(today, license.getValidTo());

            if (daysLeft <= 15) {

                String status;
                if (daysLeft < 0) {
                    status = "RED";      // expired
                } else if (daysLeft <= 15) {
                    status = "YELLOW";   // expiring soon
                } else {
                    status = "GREEN";
                }

                alerts.add(new LicenseExpiryAlertResponse(
                        license.getLicenseKey(),
                        license.getSoftwareName(),
                        license.getValidTo(),
                        daysLeft,
                        status
                ));
            }
        }
        return alerts;
    }
}
