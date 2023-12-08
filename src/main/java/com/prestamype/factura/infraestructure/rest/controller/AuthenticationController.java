package com.prestamype.factura.infraestructure.rest.controller;


import com.prestamype.factura.application.usecases.UsuarioService;
import com.prestamype.factura.domain.model.dto.request.AuthenticationRequest;
import com.prestamype.factura.domain.model.dto.response.JwtResponse;
import com.prestamype.factura.infraestructure.adapter.entity.RolEntity;
import com.prestamype.factura.infraestructure.adapter.entity.UsuarioEntity;
import com.prestamype.factura.infraestructure.adapter.entity.UsuarioRolEntity;
import com.prestamype.factura.infraestructure.rest.security.JwtUtils;
import com.prestamype.factura.infraestructure.rest.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> generarToken(@RequestBody AuthenticationRequest request) throws Exception {
        try{
            autenticar(request.getUsername(),request.getPassword());
        }catch (BadCredentialsException exception){
            exception.printStackTrace();
            //throw new Exception("Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails =  this.userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void autenticar(String username,String password) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch (DisabledException exception){
            throw  new Exception("USUARIO DESHABILITADO " + exception.getMessage());
        }catch (BadCredentialsException e){
            throw  new BadCredentialsException("Credenciales invalidas " + e.getMessage());
        }
    }


    @PostMapping("/register")
    public UsuarioEntity registerUsuario(@RequestBody UsuarioEntity usuario) throws Exception{
        usuario.setPassword(this.bCryptPasswordEncoder.encode(usuario.getPassword()));
        Set<UsuarioRolEntity> usuarioRoles = new HashSet<>();
        RolEntity rol = new RolEntity();
        rol.setRolId(2L);
        rol.setRolNombre("USER");
        UsuarioRolEntity usuarioRol = new UsuarioRolEntity();
        usuarioRol.setUsuario(usuario);
        usuarioRol.setRol(rol);

        usuarioRoles.add(usuarioRol);
        return usuarioService.guardarUsuario(usuario,usuarioRoles);
    }
}
