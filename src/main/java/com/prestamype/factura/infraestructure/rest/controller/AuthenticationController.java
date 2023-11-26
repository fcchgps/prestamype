package com.prestamype.factura.infraestructure.rest.controller;

import com.prestamype.factura.application.usecases.AuthenticationService;
import com.prestamype.factura.domain.model.dto.request.AuthenticationRequest;
import com.prestamype.factura.domain.model.dto.request.RegisterRequest;
import com.prestamype.factura.domain.model.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        try {
            ApiResponse apiResponse = new ApiResponse(authenticationService.login(request), true, null, null);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            ApiResponse apiResponse = new ApiResponse(null, false, 400, e.getMessage());
            return ResponseEntity.ok(apiResponse);
        }
    }

}
