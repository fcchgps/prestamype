package com.prestamype.factura.infraestructure.rest.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prueba")
@CrossOrigin("*")
public class PruebaController {


    @GetMapping("/listaradmin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String listaradmin() throws Exception{
        return "Lista de Admin";
    }

    @GetMapping("/listaruser")
    @PreAuthorize("hasAuthority('USER')" )
    public String listaruser() {
        return "Lista de User";
    }


}
