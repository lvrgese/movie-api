package com.lvrgese.movie_api.controller;

import com.lvrgese.movie_api.auth.service.AuthService;
import com.lvrgese.movie_api.auth.utils.AuthResponse;
import com.lvrgese.movie_api.auth.utils.LoginRequest;
import com.lvrgese.movie_api.auth.utils.RefreshTokenRequest;
import com.lvrgese.movie_api.auth.utils.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication Endpoints",description = "Endpoints for Authentication and Authorization related operations")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Register new user",description = "Register a new user with role USER")
    @ApiResponse(responseCode ="200",description = "User is successfully created")
    @PostMapping("/register")
    private ResponseEntity<AuthResponse> registerUser(@RequestBody @Valid RegisterRequest registerRequest){
        return ResponseEntity.ok(authService.registerUser(registerRequest));
    }

    @Operation(summary = "Login",description = "Login using username and password")
    @ApiResponse(responseCode ="200",description = "User is successfully authenticated")
    @PostMapping("/login")
    private ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "Get Access token",description = "Get a new access token using a valid refresh token if it isn't already expired")
    @ApiResponse(responseCode ="200")
    @PostMapping("/refresh-token")
    private ResponseEntity<AuthResponse> getAccessTokenFromRefreshToken(@RequestBody @Valid RefreshTokenRequest request){
        return ResponseEntity.ok(authService.getNewAccessToken(request.getRefreshToken()));
    }
}
