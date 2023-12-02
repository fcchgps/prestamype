package com.prestamype.factura.infraestructure.adapter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@Table(name = "financiamiento")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinanciamientoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String estado;
    @Column(name = "fechapago")
    private Date fechaPago;
    @Column(name = "monto")
    private Double monto;
    @Column(name = "factura")
    private String factura;
    @Column(name = "usuario")
    private String usuario;

    @Column(name = "creaciondate",nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date creaciondate;

    @Column(name = "aprobaciondate")
    private Date aprobaciondate;

    @Column(name = "rechazodate")
    private Date rechazodate;

    @Column(name = "useractualizacion")
    private String useractualizacion;
    //@ManyToOne(fetch = FetchType.LAZY, optional = false)
    //@JoinColumn(name = "idfactura", nullable = false)
    //private FacturaEntity facturaEntity;

}
