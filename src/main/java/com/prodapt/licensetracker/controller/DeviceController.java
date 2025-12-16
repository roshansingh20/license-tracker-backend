package com.prodapt.licensetracker.controller;

import com.prodapt.licensetracker.dto.DeviceRequest;
import com.prodapt.licensetracker.entity.Device;
import com.prodapt.licensetracker.service.DeviceService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    public Device addDevice(@RequestBody DeviceRequest request,
                            Authentication authentication) {

        return deviceService.addDevice(
                request,
                authentication.getName()
        );
    }

    @GetMapping
    public List<Device> getDevices() {
        return deviceService.getAllDevices();
    }

    @PutMapping("/{deviceId}")
    public Device updateDevice(@PathVariable String deviceId,
                               @RequestBody DeviceRequest request,
                               Authentication authentication) {

        return deviceService.updateDevice(
                deviceId,
                request,
                authentication.getName()
        );
    }

    @DeleteMapping("/{deviceId}")
    public void decommission(@PathVariable String deviceId,
                             Authentication authentication) {

        deviceService.decommissionDevice(
                deviceId,
                authentication.getName()
        );
    }
}
