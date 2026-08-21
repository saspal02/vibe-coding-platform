package com.saswat.lovable.service.impl;

import com.saswat.lovable.dto.auth.AuthResponse;
import com.saswat.lovable.dto.auth.LoginRequest;
import com.saswat.lovable.dto.auth.SignupRequest;
import com.saswat.lovable.entity.User;
import com.saswat.lovable.exception.BadRequestException;
import com.saswat.lovable.mapper.UserMapper;
import com.saswat.lovable.repository.UserRepository;
import com.saswat.lovable.security.AuthUtil;
import com.saswat.lovable.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtil authUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse signup(SignupRequest request) {

        userRepository.findByUsername(request.username()).ifPresent(user -> {
            throw new BadRequestException("Username already exists with username" + request.username());
        });

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        user = userRepository.save(user);

        String token = authUtil.generateAccessToken(user.getId(), user.getUsername());

        return new AuthResponse(token, userMapper.toUserProfileResponse(user));
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        User user = (User) authentication.getPrincipal();

        String token = authUtil.generateAccessToken(user.getId(), user.getUsername());
        return new AuthResponse(token, userMapper.toUserProfileResponse(user));
    }


}
