package com.prestamype.factura.application.service;


import com.prestamype.factura.application.usecases.AuthenticationService;
import com.prestamype.factura.domain.model.dto.request.AuthenticationRequest;
import com.prestamype.factura.domain.model.dto.request.RegisterRequest;
import com.prestamype.factura.domain.model.dto.response.AuthenticationResponse;
import com.prestamype.factura.domain.model.dto.response.RegisterResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public RegisterResponse register(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
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
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var exp = jwtService.getExpirationToken(jwtToken);
        return AuthenticationResponse.builder()
                .exp(exp)
                .user_id(user.getId())
                .user_name((user.getUsername() != null) ? user.getUsername() : null)
                .descripcion(user.getDescripcion() != null ? user.getDescripcion() : null)
                .company_id((user.getCompany() != null) ? user.getCompany().getId() : null)
                .company_description((user.getCompany() != null) ? user.getCompany().getDescripcion() : null)
                .channel_id((user.getCanal() != null) ? user.getCanal().getId() : null)
                .channel_name((user.getCanal() != null) ? user.getCanal().getDescripcion() : null)
                .sale_team_id((user.getEquipo() != null) ? user.getEquipo().getId() : null)
                .sale_team_name((user.getEquipo() != null) ? user.getEquipo().getName() : null)
                .token(jwtToken)
                .build();
    }

}
