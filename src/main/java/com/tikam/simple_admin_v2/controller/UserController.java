package com.tikam.simple_admin_v2.controller;

import com.tikam.simple_admin_v2.dto.user.UserLastLoginRequestTimeRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RequestMapping("/admin")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/last-login")
    public ResponseEntity<SseEmitter> getUserLastLoginTime(@Valid @RequestBody  UserLastLoginRequestTimeRequest userLastLoginRequestTimeRequest,
                                               HttpServletResponse response) throws IOException {
        SseEmitter sseEmitter = new SseEmitter(1800000L);
        userService.getUserLastLoginTime(sseEmitter, userLastLoginRequestTimeRequest);
        return ResponseEntity.ok(sseEmitter);
    }
}
