package com.lvrgese.movie_api.auth.utils;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Provide a valid email")
    private String email;
    @NotBlank(message = "User name is required")
    private String userName;
    @NotBlank(message = "Password is required")
    @Size(min = 5)
    private String passWord;
}
