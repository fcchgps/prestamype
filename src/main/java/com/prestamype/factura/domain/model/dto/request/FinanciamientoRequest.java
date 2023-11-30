package com.prestamype.factura.domain.model.dto.request;

import com.prestamype.factura.infraestructure.adapter.entity.FacturaEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinanciamientoRequest {
    private String invoice_id;
    private Integer net_amount;
    private String payment_date;
}
