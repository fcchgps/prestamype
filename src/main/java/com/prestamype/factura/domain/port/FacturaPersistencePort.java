package com.prestamype.factura.domain.port;

import com.prestamype.factura.infraestructure.adapter.entity.FacturaEntity;

import java.util.Optional;

public interface FacturaPersistencePort {
   FacturaEntity saveFactura(FacturaEntity factura);
   Optional<FacturaEntity> consultarFactura(String codigo, String rucEmisor, String rucProveedor);
}
