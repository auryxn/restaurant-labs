package com.auryxn.restaurantlabs.service;

import com.auryxn.restaurantlabs.dto.*;
import com.auryxn.restaurantlabs.entity.AppUser;
import com.auryxn.restaurantlabs.entity.Role;
import com.auryxn.restaurantlabs.exception.BadRequestException;
import com.auryxn.restaurantlabs.repository.AppUserRepository;
import com.auryxn.restaurantlabs.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse register(AuthRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new BadRequestException("Username is already taken");
        }
        AppUser user = AppUser.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .roles(Set.of(Role.ROLE_USER))
                .build();
        userRepository.save(user);
        log.info("Registered user {}", request.username());
        return tokenFor(request.username());
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        log.info("User {} logged in", request.username());
        return tokenFor(request.username());
    }

    private AuthResponse tokenFor(String username) {
        var userDetails = userDetailsService.loadUserByUsername(username);
        String token = jwtService.generateToken(userDetails);
        Set<String> roles = userDetails.getAuthorities().stream().map(Object::toString).collect(Collectors.toSet());
        return new AuthResponse(token, "Bearer", username, roles);
    }
}
