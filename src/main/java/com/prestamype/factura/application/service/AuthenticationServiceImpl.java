package com.prestamype.factura.application.service;


import com.prestamype.factura.application.enumeral.Role;
import com.prestamype.factura.application.usecases.AuthenticationService;
import com.prestamype.factura.application.usecases.JwtService;
import com.prestamype.factura.domain.model.dto.request.AuthenticationRequest;
import com.prestamype.factura.domain.model.dto.request.RegisterRequest;
import com.prestamype.factura.domain.model.dto.response.AuthenticationResponse;
import com.prestamype.factura.domain.model.dto.response.RegisterResponse;
import com.prestamype.factura.domain.port.UserPersistencePort;
import com.prestamype.factura.infraestructure.adapter.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserPersistencePort userPersistencePort;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @Override
    public RegisterResponse register(RegisterRequest request) {
        var user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userPersistencePort.saveUser(user);

        return RegisterResponse.builder()
                .message("Registered Successfully")
                .build();
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userPersistencePort.findByUsername(request.getUsername())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        var exp = jwtService.getExpirationToken(jwtToken);

        return AuthenticationResponse.builder()
                .exp(exp)
                .user_id(user.getId())
                .user_name((user.getUsername() != null) ? user.getUsername() : null)
                .token(jwtToken)
                .build();
    }

}
