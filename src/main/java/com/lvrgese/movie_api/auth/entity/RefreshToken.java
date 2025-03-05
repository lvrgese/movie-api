package com.lvrgese.movie_api.auth.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokenId;
    @Column(nullable = false,length = 500)
    @NotBlank
    private String refreshToken;
    @NotNull
    private Instant expirationTime;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;
}
