package com.lvrgese.movie_api.auth.service;

import com.lvrgese.movie_api.auth.entity.RefreshToken;
import com.lvrgese.movie_api.auth.entity.User;
import com.lvrgese.movie_api.auth.entity.UserRole;
import com.lvrgese.movie_api.auth.repository.UserRepository;
import com.lvrgese.movie_api.auth.utils.AuthResponse;
import com.lvrgese.movie_api.auth.utils.LoginRequest;
import com.lvrgese.movie_api.auth.utils.RegisterRequest;
import com.lvrgese.movie_api.exceptions.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse registerUser(RegisterRequest req){

        if(userRepository.findByUserName(req.getUserName()).isPresent())
            throw new UserAlreadyExistsException("This user already Exists.");
        User user = User.builder()
                .userName(req.getUserName())
                .passWord(passwordEncoder.encode(req.getPassWord()))
                .email(req.getEmail())
                .name(req.getName())
                .role(UserRole.USER)
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .build();
        User savedUser = userRepository.save(user);

        var accessToken = jwtService.generateToken(savedUser);
        var refreshToken = refreshTokenService.createRefreshToken(savedUser.getUsername());


        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

    public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(),request.getPassWord())
        );
        User user = userRepository.findByUserName(request.getUserName()).orElseThrow(
                ()-> new UsernameNotFoundException("User name not found")
        );
        var accessToken = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(user.getUsername());
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

    public AuthResponse getNewAccessToken(String refreshToken){
        RefreshToken token = refreshTokenService.validateRefreshToken(refreshToken);
        User user = token.getUser();

        var accessToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
