package com.hotel.auth;

import com.hotel.dto.LoginRequest;
import com.hotel.dto.RegisterRequest;
import com.hotel.dto.RegisterResponse;
import com.hotel.dto.LoginResponse;
import com.hotel.entity.User;
import com.hotel.repository.UserRepository;
import com.hotel.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;

import com.hotel.config.Redis.TokenStoreService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final TokenStoreService redisService;

    @Override
    public RegisterResponse register(RegisterRequest request) {


        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .role(request.getRole())
                .build();

        userRepository.save(user);

        return new RegisterResponse(true, "✅ User registered successfully!");
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        System.out.println("HELLLOO" + request);

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("❌ Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("❌ Invalid username or password");
        }

        // Generate tokens using userId
        String accessToken = jwtUtil.generateAccessToken(user.getId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId());

        System.out.println("accessToken" + accessToken);
        System.out.println("refreshToken" + refreshToken);


        // Store in Redis using TokenStoreService
        redisService.storeAccessToken(accessToken, user.getId());
        redisService.storeRefreshToken(refreshToken, user.getId());

        return new LoginResponse( user.getId(), "✅ Login successful!", accessToken, refreshToken);
    }

    // ✅ Refresh token logic
    public LoginResponse refreshToken(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String userId = jwtUtil.extractUserId(refreshToken);

        // Check refresh token in Redis
        if (!redisService.isRefreshTokenValid(refreshToken)) {
            throw new RuntimeException("Refresh token expired or invalid");
        }

        // Generate new access token
        String newAccessToken = jwtUtil.generateAccessToken(userId);
        redisService.storeAccessToken(newAccessToken, Long.valueOf(userId));

        return new LoginResponse(userId, "🔄 Token refreshed!", newAccessToken, refreshToken);
    }

    @Override
    public void logout(String accessToken, String refreshToken) {
        // Delete both tokens from Redis
        redisService.deleteAccessToken(accessToken);
        redisService.deleteRefreshToken(refreshToken);
    }
}
