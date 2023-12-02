package com.prestamype.factura.domain.model.dto.request;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Setter
@Getter
public class FinanciamientoRequest {
    private String invoice_id;
    private Double net_amount=-1.0;
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date payment_date;
    private String usuario;
}
