package com.tikam.simple_admin_v2.service.auth.refreshToken;

import com.tikam.simple_admin_v2.dto.auth.RefreshTokenRequest;
import com.tikam.simple_admin_v2.dto.auth.RefreshTokenResponse;
import com.tikam.simple_admin_v2.entity.RefreshToken;
import com.tikam.simple_admin_v2.entity.User;
import com.tikam.simple_admin_v2.exception.AdminException;
import com.tikam.simple_admin_v2.exception.ErrorCode;
import com.tikam.simple_admin_v2.repository.RefreshTokenRepository;
import com.tikam.simple_admin_v2.repository.UserRepository;
import com.tikam.simple_admin_v2.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private static final int REFRESH_TOKEN_DURATION = 7;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtils jwtUtils;
    @Override
    @Transactional
    public String createRefreshToken(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AdminException(ErrorCode.NOT_FOUND, "User not found with id " + userId));

        String refreshToken = UUID.randomUUID().toString();
        Instant expiryDate = Instant.now().plus(REFRESH_TOKEN_DURATION, ChronoUnit.DAYS);

        RefreshToken refreshTokenObj = RefreshToken.builder()
                .token(refreshToken)
                .expiryDate(expiryDate)
                .user(user)
                .build();

        refreshTokenRepository.save(refreshTokenObj);
        log.info("the refresh token is saved with id {}", refreshToken);
        return refreshToken;
    }


    @Override
    @Transactional
    public Boolean verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken == null) {
            throw new RuntimeException("refresh token should be null");
        }
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.deleteById(refreshToken.getId());
            throw new AdminException(ErrorCode.TOKEN_EXPIRED);
        }
        return true;

    }

    @Override
    @Transactional
    public Boolean deleteByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->new  AdminException(ErrorCode.NOT_FOUND, "the user Id is not found"));
        refreshTokenRepository.deleteByUser(user);
        return true;
    }

    @Transactional
    public RefreshTokenResponse generateJwtToken(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken  = refreshTokenRequest.refreshToken();
        System.out.println(refreshToken);
        RefreshToken refreshTokenObj = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new AdminException(ErrorCode.NOT_FOUND, "refresh token not found"));
        verifyExpiration(refreshTokenObj);

            User user = refreshTokenObj.getUser();
            refreshTokenRepository.delete(refreshTokenObj);
            //forcefully deleting refresh token immediately
            refreshTokenRepository.flush();

            String newRefreshToken = createRefreshToken(user.getUserId());
            String newJwtToken =  jwtUtils.generateToken(user.getUserId(), user.getEmailAddress());
            return RefreshTokenResponse.builder()
                    .token(newJwtToken)
                    .refresh_token(newRefreshToken)
                    .build();
    }
}
