package com.prestamype.factura.domain.port;

import com.prestamype.factura.infraestructure.adapter.entity.FacturaEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface FacturaPersistencePort {
   FacturaEntity saveFactura(FacturaEntity factura);
   Optional<FacturaEntity> findFirstByCodigoAndRucEmisorAndRucProveedor(String codigo, String rucEmisor, String rucProveedor );
   List<FacturaEntity> consultarFacturasPorUsuario(String usuario,String codigo,String rucEmisor,String rucProveedor);

}
