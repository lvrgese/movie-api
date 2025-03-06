package com.lvrgese.movie_api.auth.utils;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    @NotBlank(message = "Username is required")
    private String userName;
    @NotBlank(message = "Password is required")
    private String passWord;
}
