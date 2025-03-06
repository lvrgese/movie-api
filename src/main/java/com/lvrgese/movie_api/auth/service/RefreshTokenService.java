package com.lvrgese.movie_api.auth.service;

import com.lvrgese.movie_api.auth.entity.RefreshToken;
import com.lvrgese.movie_api.auth.entity.User;
import com.lvrgese.movie_api.auth.repository.RefreshTokenRepository;
import com.lvrgese.movie_api.auth.repository.UserRepository;
import com.lvrgese.movie_api.exceptions.InvalidRefreshTokenException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final UserRepository userRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(String userName){
        User user = userRepository.findByUserName(userName).orElseThrow(
                ()->new UsernameNotFoundException("User not found")
        );

        RefreshToken refreshToken = user.getRefreshToken();
        long expirationTime = 7*24*60*60*1000;
        if(refreshToken != null)
            return refreshToken;
        RefreshToken generatedToken =  RefreshToken.builder()
                .expirationTime(Instant.now().plusMillis(expirationTime))
                .refreshToken(UUID.randomUUID().toString())
                .user(user)
                .build();
        refreshTokenRepository.save(generatedToken);

        return generatedToken;
    }

    public RefreshToken validateRefreshToken(String token){
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(token)
                .orElseThrow(()-> new InvalidRefreshTokenException("Refresh token is not valid"));
        if(refreshToken.getExpirationTime().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(refreshToken);
            throw new InvalidRefreshTokenException("Refresh token expired");
        }
        return refreshToken;
    }

}
