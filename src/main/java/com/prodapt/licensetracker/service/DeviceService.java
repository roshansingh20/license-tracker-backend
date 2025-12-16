package com.prodapt.licensetracker.service;

import com.prodapt.licensetracker.audit.AuditLog;
import com.prodapt.licensetracker.audit.AuditLogRepository;
import com.prodapt.licensetracker.dto.DeviceRequest;
import com.prodapt.licensetracker.entity.Device;
import com.prodapt.licensetracker.entity.DeviceStatus;
import com.prodapt.licensetracker.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final AuditLogRepository auditLogRepository;

    public DeviceService(DeviceRepository deviceRepository,
                         AuditLogRepository auditLogRepository) {
        this.deviceRepository = deviceRepository;
        this.auditLogRepository = auditLogRepository;
    }

    public Device addDevice(DeviceRequest request, String performedBy) {

        Device device = new Device(
                request.getDeviceId(),
                request.getIpAddress(),
                request.getType(),
                request.getLocation(),
                request.getModel(),
                request.getVendor(),
                request.getStatus()
        );

        deviceRepository.save(device);

        logAudit("ADD_DEVICE", performedBy, device.getDeviceId(), "SUCCESS");

        return device;
    }

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public Device updateDevice(String deviceId, DeviceRequest request, String performedBy) {

        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Device not found"));

        device.setIpAddress(request.getIpAddress());
        device.setType(request.getType());
        device.setLocation(request.getLocation());
        device.setModel(request.getModel());
        device.setVendor(request.getVendor());
        device.setStatus(request.getStatus());

        deviceRepository.save(device);

        logAudit("UPDATE_DEVICE", performedBy, deviceId, "SUCCESS");

        return device;
    }

    public void decommissionDevice(String deviceId, String performedBy) {

        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Device not found"));

        device.setStatus(DeviceStatus.DECOMMISSIONED);
        deviceRepository.save(device);

        logAudit("DECOMMISSION_DEVICE", performedBy, deviceId, "SUCCESS");
    }

    private void logAudit(String action, String user, String ref, String status) {
        auditLogRepository.save(new AuditLog(
                null,
                action,
                user,
                ref,
                LocalDateTime.now(),
                status
        ));
    }
}
