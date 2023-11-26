package com.prestamype.factura.domain.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FacturaDto {
    private String codigo;
    private String fechaEmision;
    private Double monto;
    private String moneda;
    private String rucProveedor;
    private String rucEmisor;
}
