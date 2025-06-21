package com.metinkuzey.mcp.controller;

import com.metinkuzey.mcp.dto.TokenRefreshRequest;
import com.metinkuzey.mcp.dto.TokenRefreshResponse;
import com.metinkuzey.mcp.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/refresh-token")
@Tag(name = "Refresh Token", description = "Operations related to JWT refresh tokens")
public class RefreshTokenController {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Token successfully refreshed"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid refresh token"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Refresh token expired")
    })
    @PostMapping
    @Operation(summary = "Refresh JWT token", description = "Use the refresh token to generate a new access token.")
    public ResponseEntity<TokenRefreshResponse> refreshToken(@RequestBody TokenRefreshRequest request) {
        return ResponseEntity.ok(refreshTokenService.refreshToken(request));
    }
}
