package com.tikam.simple_admin_v2.controller;

import com.tikam.simple_admin_v2.dto.device.DeviceStateRequest;
import com.tikam.simple_admin_v2.service.device.DeviceServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class deviceController {
    private static final long DEVICE_STATS_SSE_TIMEOUT = 1_800_000L;
    private final DeviceServiceImpl deviceService;

    @PostMapping("/device/stats")
    public ResponseEntity<SseEmitter> getDeviceStats(@RequestBody @Valid DeviceStateRequest
                                                                 deviceStateRequest) {
        SseEmitter sseEmitter = new SseEmitter(DEVICE_STATS_SSE_TIMEOUT);
        deviceService.getDevicStats(sseEmitter, deviceStateRequest);
        return ResponseEntity.ok(sseEmitter);
    }


}
