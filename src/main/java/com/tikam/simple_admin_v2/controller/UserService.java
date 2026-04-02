package com.tikam.simple_admin_v2.controller;

import com.tikam.simple_admin_v2.dto.user.LoginInfoDto;
import com.tikam.simple_admin_v2.dto.user.UserLastLoginRequestTimeRequest;
import com.tikam.simple_admin_v2.exception.AdminException;
import com.tikam.simple_admin_v2.exception.ErrorCode;
import com.tikam.simple_admin_v2.repository.query.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserQueryRepository userQueryRepository;

    @Value("${batch.size}")
    private static final int BATCH_SIZE = 100;
    @Async
    public void getUserLastLoginTime(SseEmitter sseEmitter, UserLastLoginRequestTimeRequest userLastLoginRequestTimeRequest) throws IOException {
        // Implementation for retrieving user last login time
        log.info("SSE start server send events");
        long start = System.currentTimeMillis();
        String companyCode = userLastLoginRequestTimeRequest.getCompanyCode();
        String suborgCode = userLastLoginRequestTimeRequest.getSuborgCode();

        Optional<Long> userCount = userQueryRepository.getLastUserCount(companyCode, suborgCode);
        Long lastLoginCount = userCount.orElseThrow(() -> new AdminException(ErrorCode.NOT_FOUND, "User not found"));
        log.info("company user count: (companyCode {}, suborgCode: {}, count: {}",companyCode, suborgCode, lastLoginCount);

        for(int i = 0; i < lastLoginCount; i += BATCH_SIZE) {
            Map<String, LoginInfoDto> lastLoginInfoMap = userQueryRepository.getLastLoginInfo(companyCode, suborgCode, i,BATCH_SIZE);
            List<LoginInfoDto> list = new ArrayList<>(lastLoginInfoMap.values().stream().toList());
           try {
               sseEmitter.send(list);
//               list.clear();
           } catch (Exception e) {
               log.info("SSE error occurred while sending the messages");
               sseEmitter.completeWithError(new AdminException(ErrorCode.INTERNAL_SERVER_ERROR));
           }
        }
        sseEmitter.complete();
        log.info("end server sent events (elapsed time: {}ms", System.currentTimeMillis() - start);
    }



}
