package com.bulk.kyro.api.controllers;

import com.bulk.kyro.api.models.user.requests.LoginRequest;
import com.bulk.kyro.api.models.user.requests.RegisterRequest;
import com.bulk.kyro.api.models.user.responses.UserResponse;
import com.bulk.kyro.api.models.user.responses.UserTokenResponse;
import com.bulk.kyro.api.utils.JwtUtils;
import com.bulk.kyro.bll.services.AuthService;
import com.bulk.kyro.dl.entities.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtils jwtUtils;

    @PreAuthorize("isAnonymous()")
    @PostMapping("/register")
    public ResponseEntity<UserTokenResponse> register(@Valid @RequestBody RegisterRequest request) {
        UserEntity user = authService.register(request.toUser());
        UserTokenResponse response = mapUser(user);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/login")
    public ResponseEntity<UserTokenResponse> login(@Valid @RequestBody LoginRequest request) {
        UserEntity user = authService.login(request.username(), request.password());
        UserTokenResponse response = mapUser(user);
        log.info("User {} logged in successfully", user.getUsername());
        return ResponseEntity.ok(response);
    }

    private UserTokenResponse mapUser(UserEntity user) {
        UserResponse userResponse = UserResponse.fromUser(user);
        String token = jwtUtils.generateToken(user);
        return new UserTokenResponse(userResponse, token);
    }
}
