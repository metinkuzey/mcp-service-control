package com.metinkuzey.mcp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.metinkuzey.mcp.dto.LoginRequest;
import com.metinkuzey.mcp.dto.RegisterRequest;
import com.metinkuzey.mcp.dto.TokenRefreshRequest;
import com.metinkuzey.mcp.model.RefreshToken;
import com.metinkuzey.mcp.model.User;
import com.metinkuzey.mcp.repository.UserRepository;
import com.metinkuzey.mcp.security.JwtTokenProvider;
import com.metinkuzey.mcp.service.RefreshTokenService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Operations related to user authentication")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and return access/refresh tokens")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtTokenProvider.generateToken(authentication);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return ResponseEntity.ok(new JwtAuthenticationResponse(token, refreshToken.getToken()));
    }

    @PostMapping("/register")
    @Operation(summary = "User registration", description = "Register a new user account")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh token", description = "Refresh JWT token using the refresh token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtTokenProvider.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new JwtAuthenticationResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not valid."));
    }

    @io.swagger.v3.oas.annotations.media.Schema(description = "Response containing access and refresh tokens")
    public static class JwtAuthenticationResponse {
        @io.swagger.v3.oas.annotations.media.Schema(description = "JWT access token")
        private String accessToken;

        @io.swagger.v3.oas.annotations.media.Schema(description = "JWT refresh token")
        private String refreshToken;

        public JwtAuthenticationResponse(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }
    }
}
