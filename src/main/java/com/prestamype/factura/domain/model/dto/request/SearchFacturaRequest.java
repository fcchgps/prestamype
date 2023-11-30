package com.prestamype.factura.domain.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchFacturaRequest {
    private String codigo;
    private String rucemisor;
    private String rucproveedor;
}
