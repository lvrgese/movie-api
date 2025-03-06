package com.lvrgese.movie_api.auth.utils;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequest {
    @NotBlank(message = "Token is empty")
    private String refreshToken;
}
