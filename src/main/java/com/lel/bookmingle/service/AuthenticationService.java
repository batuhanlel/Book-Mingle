package com.lel.bookmingle.service;

import com.lel.bookmingle.dto.request.authorization.LoginRequest;
import com.lel.bookmingle.dto.request.authorization.RegisterRequest;
import com.lel.bookmingle.dto.response.AuthenticationResponse;
import com.lel.bookmingle.enums.Role;
import com.lel.bookmingle.exception.BadCredentialsException;
import com.lel.bookmingle.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .name(request.name())
                .surname(request.surname())
                .birthDate(request.birthDate())
                .phoneNumber(request.phoneNumber())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();
        userService.saveUser(user);

        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(LoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Bad Credentials");
        }
        User user = userService.findUserByEmail(request.email());

        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
