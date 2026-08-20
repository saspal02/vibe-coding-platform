package com.saswat.lovable.service.impl;

import com.saswat.lovable.dto.auth.AuthResponse;
import com.saswat.lovable.dto.auth.LoginRequest;
import com.saswat.lovable.dto.auth.SignupRequest;
import com.saswat.lovable.entity.User;
import com.saswat.lovable.exception.BadRequestException;
import com.saswat.lovable.mapper.UserMapper;
import com.saswat.lovable.repository.UserRepository;
import com.saswat.lovable.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse signup(SignupRequest request) {

        userRepository.findByUsername(request.username()).ifPresent(user -> {
            throw new BadRequestException("Username already exists with username" + request.username());
        });

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        user = userRepository.save(user);

        return new AuthResponse("dummy", userMapper.toUserProfileResponse(user));



    }

    @Override
    public AuthResponse login(LoginRequest request) {
        return null;
    }


}
