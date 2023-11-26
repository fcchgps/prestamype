package com.prestamype.factura.application.usecases;


import com.prestamype.factura.domain.model.dto.request.AuthenticationRequest;
import com.prestamype.factura.domain.model.dto.request.RegisterRequest;
import com.prestamype.factura.domain.model.dto.response.AuthenticationResponse;
import com.prestamype.factura.domain.model.dto.response.RegisterResponse;

public interface AuthenticationService {
    RegisterResponse register(RegisterRequest request);
    AuthenticationResponse login(AuthenticationRequest request);

}
