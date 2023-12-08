package com.prestamype.factura.domain.port;

import com.prestamype.factura.domain.model.dto.request.FinanciamientoRequest;
import com.prestamype.factura.infraestructure.adapter.entity.FacturaEntity;
import com.prestamype.factura.infraestructure.adapter.entity.FinanciamientoEntity;

import java.util.List;
import java.util.Optional;

public interface FacturaPersistencePort {
   FacturaEntity saveFactura(FacturaEntity facturaEntity);
   Optional<FacturaEntity> findFirstByCodigoAndRucEmisorAndRucProveedor(String codigo, String rucEmisor, String rucProveedor );
   List<FacturaEntity> consultarFacturasPorUsuario(String usuario,String codigo,String rucEmisor,String rucProveedor);
   Optional<FinanciamientoEntity> findFirstByFacturaAndUsuario(String codigo, String usuario);
   void saveFinanciamiento(FinanciamientoEntity financiamientoEntity);
   //FacturaEntity findByCodigoAndUsuario(String codigo,String usuario);
   Optional<FacturaEntity> findByCodigoAndUsuario(String codigo,String usuario);
   List<FinanciamientoEntity> consultarFinanciamientoPorUsuario(String usuario,String factura,String rucProveedor);
   List<FinanciamientoEntity> consultarSolicitudFinanciamiento(String factura,String rucProveedor);
   //void aprobarRechazarFinanciamiento(Long idFinanciamiento);
   Optional<FinanciamientoEntity> findById(Long idFinanciamiento);
}
