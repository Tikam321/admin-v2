package com.tikam.simple_admin_v2.service.device;

import com.tikam.simple_admin_v2.dto.device.DeviceStatQueryDto;
import com.tikam.simple_admin_v2.dto.device.DeviceState;
import com.tikam.simple_admin_v2.dto.device.DeviceStateDto;
import com.tikam.simple_admin_v2.dto.device.DeviceStateRequest;
import com.tikam.simple_admin_v2.exception.AdminException;
import com.tikam.simple_admin_v2.exception.ErrorCode;
import com.tikam.simple_admin_v2.repository.query.DeviceQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class DeviceServiceImpl {
    private final DeviceQueryRepository deviceQueryRepository;

    @Value("${device.stats.limit:500}")
    private long queryLimit;

    @Value("${device.stats.responseSize:100}")
    private long responseSize;


    @Async
    public void getDevicStats(SseEmitter sseEmitter, DeviceStateRequest deviceStateRequest) {
        log.info("(SSE) starts sending device stats events");
        long startTime = System.currentTimeMillis();
        DeviceStatQueryDto deviceStatQueryDto = DeviceStatQueryDto.from(deviceStateRequest);
        long deviceCount = deviceQueryRepository.getDeviceCount(deviceStatQueryDto);
        log.info("target device count: {}",deviceCount);

        List<DeviceState> responselist = new ArrayList<>();
        for(long offset = 0; offset < deviceCount; offset += 100) {
            List<DeviceStateDto> devicests = deviceQueryRepository.getDeviceStats(deviceStatQueryDto, offset, 100);
            for(DeviceStateDto deviceStateDto : devicests) {
                responselist.add(DeviceState.from(deviceStateDto));
                if (responselist.size() == responseSize)
                  sendDeviceStats(sseEmitter, responselist);
            }
        }
        if (!responselist.isEmpty()) {
            sendDeviceStats(sseEmitter,responselist);
        }
        sseEmitter.complete();
        log.info("(SSE) ends sending device stats complete (elapsed time: {})", System.currentTimeMillis() - startTime);
    }

    private void sendDeviceStats(SseEmitter sseEmitter, List<DeviceState> responselist) {
        try {
            log.info("send device stats count: {}", responselist.size());
            sseEmitter.send(new ArrayList<>(responselist));
        } catch (IOException e) {
            log.info("(SSE) error occurred while sending events ", e);
            sseEmitter.completeWithError(new AdminException(ErrorCode.INTERNAL_SERVER_ERROR));
        } finally {
            responselist.clear();
        }
    }


}
