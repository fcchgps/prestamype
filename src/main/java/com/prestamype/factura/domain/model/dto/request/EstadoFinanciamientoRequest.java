package com.prestamype.factura.domain.model.dto.request;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstadoFinanciamientoRequest {
    private Long idFinanciamiento;
    private String estado;

}
