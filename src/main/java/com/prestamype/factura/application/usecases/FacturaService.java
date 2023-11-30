package com.prestamype.factura.application.usecases;

import com.prestamype.factura.domain.model.dto.RequestXmlDTO;
import com.prestamype.factura.infraestructure.adapter.entity.FacturaEntity;

import java.util.List;
import java.util.Optional;

public interface FacturaService {
    FacturaEntity consultarFactura(FacturaEntity factura);
    FacturaEntity xmlToFactura(RequestXmlDTO request);
    FacturaEntity saveFactura(FacturaEntity factura);
    List<FacturaEntity> consultarFacturasPorUsuario(String usuario,String codigo,String rucEmisor,String rucProveedor);

}
