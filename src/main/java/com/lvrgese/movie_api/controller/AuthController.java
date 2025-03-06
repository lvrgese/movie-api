package com.lvrgese.movie_api.controller;

import com.lvrgese.movie_api.auth.service.AuthService;
import com.lvrgese.movie_api.auth.utils.AuthResponse;
import com.lvrgese.movie_api.auth.utils.LoginRequest;
import com.lvrgese.movie_api.auth.utils.RefreshTokenRequest;
import com.lvrgese.movie_api.auth.utils.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    private ResponseEntity<AuthResponse> registerUser(@RequestBody @Valid RegisterRequest registerRequest){
        return ResponseEntity.ok(authService.registerUser(registerRequest));
    }

    @PostMapping("/login")
    private ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh-token")
    private ResponseEntity<AuthResponse> getAccessTokenFromRefreshToken(@RequestBody @Valid RefreshTokenRequest request){
        return ResponseEntity.ok(authService.getNewAccessToken(request.getRefreshToken()));
    }
}
