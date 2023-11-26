package com.prestamype.factura.application.usecases;

import com.prestamype.factura.domain.model.dto.RequestXmlDTO;
import com.prestamype.factura.infraestructure.adapter.entity.FacturaEntity;

public interface FacturaService1 {
    FacturaEntity consultarFactura(FacturaEntity factura);
    FacturaEntity xmlToFactura(RequestXmlDTO request);
    FacturaEntity saveFactura(FacturaEntity factura);
}
