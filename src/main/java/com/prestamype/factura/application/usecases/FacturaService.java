package com.prestamype.factura.application.usecases;

import com.prestamype.factura.domain.model.dto.RequestXmlDTO;
import com.prestamype.factura.infraestructure.adapter.entity.FacturaEntity;

public interface FacturaService {
    FacturaEntity consultarFactura(FacturaEntity factura);
    FacturaEntity xmlToFactura(RequestXmlDTO request);
    FacturaEntity saveFactura(FacturaEntity factura);
}
