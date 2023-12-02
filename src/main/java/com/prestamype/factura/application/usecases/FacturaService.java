package com.prestamype.factura.application.usecases;

import com.prestamype.factura.domain.model.dto.RequestXmlDTO;
import com.prestamype.factura.domain.model.dto.request.EstadoFinanciamientoRequest;
import com.prestamype.factura.domain.model.dto.request.FinanciamientoRequest;
import com.prestamype.factura.infraestructure.adapter.entity.FacturaEntity;
import com.prestamype.factura.infraestructure.adapter.entity.FinanciamientoEntity;

import java.util.List;

public interface FacturaService {
    FacturaEntity consultarFactura(FacturaEntity factura);
    FacturaEntity xmlToFactura(RequestXmlDTO request);
    FacturaEntity saveFactura(FacturaEntity factura);
    List<FacturaEntity> consultarFacturasPorUsuario(String usuario,String codigo,String rucEmisor,String rucProveedor);
    void financiamiento(FinanciamientoRequest financiamientoRequest);
    List<FinanciamientoEntity> consultarFinanciamientoPorUsuario(String usuario,String factura,String rucProveedor);
    List<FinanciamientoEntity> consultarSolicitudFinanciamiento(String factura,String rucProveedor);
    void aprobarRechazarFinanciamiento(EstadoFinanciamientoRequest estadoFinanciamientoRequest);
}
