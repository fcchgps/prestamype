package com.prestamype.factura.infraestructure.rest.controller;

import com.prestamype.factura.domain.port.FacturaPersistencePort;
import com.prestamype.factura.infraestructure.adapter.entity.FacturaEntity;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
@RequestMapping("/prueba")
@CrossOrigin("*")
public class PruebaController {
    FacturaPersistencePort facturaPersistencePort;

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


    @GetMapping("/lisarXml")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String lisarXml(@RequestBody FacturaEntity factura) throws Exception{

        Optional<FacturaEntity> opt = facturaPersistencePort.findFirstByCodigoAndRucEmisorAndRucProveedor(factura.getCodigo(), factura.getRucEmisor(), factura.getRucProveedor());
        if (!opt.isEmpty()) {
            throw new DuplicateKeyException("Ya existe la Factura con el id: " + factura.getCodigo());
        }
    return  null;
    }

}
